package com.contactbook.database;

import com.contactbook.model.Contact;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    // Database connection details
    // Using your provided credentials and database name
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/connect_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Kgisl@123";

    public DatabaseManager() {
        try {
            // Load the MySQL JDBC driver.
            // This line ensures the driver is registered with DriverManager.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Make sure the JAR is in your classpath.");
            e.printStackTrace();
            // Propagate as a runtime exception to indicate a critical setup error
            throw new RuntimeException("Failed to load JDBC driver.", e);
        }
    }

    /**
     * Establishes a connection to the MySQL database.
     * @return A Connection object.
     * @throws SQLException if a database access error occurs.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Adds a new contact to the database.
     * @param contact The Contact object to add. Its ID will be updated after successful insertion.
     * @return true if the contact was added successfully, false otherwise.
     */
    public boolean addContact(Contact contact) {
        String sql = "INSERT INTO contacts (name, phone, email) VALUES (?, ?, ?)";
        // Use try-with-resources to ensure resources are closed automatically
        try (Connection conn = getConnection();
             // RETURN_GENERATED_KEYS allows retrieval of auto-incremented ID
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getPhone());
            pstmt.setString(3, contact.getEmail());

            int affectedRows = pstmt.executeUpdate(); // Execute the insert statement

            if (affectedRows > 0) {
                // If rows were affected, try to get the generated ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contact.setId(generatedKeys.getInt(1)); // Set the generated ID back to the Contact object
                    }
                }
                System.out.println("DatabaseManager: Contact added successfully: " + contact.getName() + " (ID: " + contact.getId() + ")");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("DatabaseManager: Error adding contact: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all contacts from the database.
     * @return A List of Contact objects. Returns an empty list if no contacts or an error occurs.
     */
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT id, name, phone, email FROM contacts";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) { // Execute the select query

            while (rs.next()) { // Iterate through the result set
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                contacts.add(new Contact(id, name, phone, email)); // Create Contact object and add to list
            }
            System.out.println("DatabaseManager: Retrieved " + contacts.size() + " contacts from database.");
        } catch (SQLException e) {
            System.err.println("DatabaseManager: Error retrieving contacts: " + e.getMessage());
            e.printStackTrace();
        }
        return contacts;
    }

    /**
     * Updates an existing contact in the database.
     * @param contact The Contact object with updated information (its ID is used to identify the record).
     * @return true if the contact was updated successfully, false otherwise.
     */
    public boolean updateContact(Contact contact) {
        String sql = "UPDATE contacts SET name = ?, phone = ?, email = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getPhone());
            pstmt.setString(3, contact.getEmail());
            pstmt.setInt(4, contact.getId()); // Use ID for WHERE clause

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("DatabaseManager: Contact updated successfully: " + contact.getName() + " (ID: " + contact.getId() + ")");
                return true;
            } else {
                System.out.println("DatabaseManager: No contact found for update with ID: " + contact.getId());
            }
        } catch (SQLException e) {
            System.err.println("DatabaseManager: Error updating contact: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a contact from the database based on its ID.
     * @param contactId The ID of the contact to delete.
     * @return true if the contact was deleted successfully, false otherwise.
     */
    public boolean deleteContact(int contactId) {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, contactId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("DatabaseManager: Contact deleted successfully with ID: " + contactId);
                return true;
            } else {
                System.out.println("DatabaseManager: No contact found for deletion with ID: " + contactId);
            }
        } catch (SQLException e) {
            System.err.println("DatabaseManager: Error deleting contact: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
