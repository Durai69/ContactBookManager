package com.contactbook.gui;

import com.contactbook.model.Contact;
import com.contactbook.service.ContactService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector; // Used by DefaultTableModel

public class ContactBookGUI extends JFrame {

    private ContactService contactService;

    // GUI Components
    private JTextField nameField, phoneField, emailField;
    private JButton addButton, updateButton, deleteButton, clearButton, refreshButton;
    private JTable contactTable;
    private DefaultTableModel tableModel; // Using DefaultTableModel for simplicity
    private JLabel selectedContactIdLabel; // To display the ID of the selected contact

    public ContactBookGUI() {
        // Initialize the ContactService, which in turn initializes DatabaseManager
        contactService = new ContactService();

        // --- Frame Setup ---
        setTitle("Contact Book Manager");
        setSize(800, 600); // Increased size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(new BorderLayout(10, 10)); // Add gaps between regions

        // --- Input Panel (North) ---
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Contact Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Selected ID:"), gbc);

        // Text Fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Allow text fields to expand horizontally
        nameField = new JTextField(25);
        inputPanel.add(nameField, gbc);

        gbc.gridy = 1;
        phoneField = new JTextField(25);
        inputPanel.add(phoneField, gbc);

        gbc.gridy = 2;
        emailField = new JTextField(25);
        inputPanel.add(emailField, gbc);

        // Selected Contact ID Label
        gbc.gridy = 3;
        selectedContactIdLabel = new JLabel("None");
        selectedContactIdLabel.setFont(selectedContactIdLabel.getFont().deriveFont(Font.BOLD));
        inputPanel.add(selectedContactIdLabel, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // --- Button Panel (Center-Top, inside main frame) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centered flow layout with gaps
        addButton = new JButton("Add Contact");
        updateButton = new JButton("Update Contact");
        deleteButton = new JButton("Delete Contact");
        clearButton = new JButton("Clear Fields");
        refreshButton = new JButton("Refresh Table");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.CENTER); // Place buttons below input fields

        // --- Table Panel (South/Remaining Space) ---
        String[] columnNames = {"ID", "Name", "Phone", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable directly in the table
                return false;
            }
        };
        contactTable = new JTable(tableModel);
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only allow single row selection
        contactTable.getTableHeader().setReorderingAllowed(false); // Prevent column reordering
        JScrollPane scrollPane = new JScrollPane(contactTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Contacts List"));
        add(scrollPane, BorderLayout.SOUTH); // Adjust to SOUTH to make it fill remaining space

        // --- Action Listeners ---
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateTable();
            }
        });

        // Add mouse listener to table for selection
        contactTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = contactTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get data from selected row
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    String name = (String) tableModel.getValueAt(selectedRow, 1);
                    String phone = (String) tableModel.getValueAt(selectedRow, 2);
                    String email = (String) tableModel.getValueAt(selectedRow, 3);

                    // Populate text fields
                    selectedContactIdLabel.setText(String.valueOf(id));
                    nameField.setText(name);
                    phoneField.setText(phone);
                    emailField.setText(email);
                }
            }
        });

        // --- Initial Population ---
        populateTable(); // Load contacts into the table when the GUI starts
        clearFields(); // Clear fields initially
    }

    /**
     * Populates the JTable with contact data from the ContactService.
     */
    private void populateTable() {
        // Clear existing data from the table model
        tableModel.setRowCount(0);
        List<Contact> contacts = contactService.getAllContacts();
        for (Contact contact : contacts) {
            // Create a Vector (or Object array) for each row
            Vector<Object> row = new Vector<>();
            row.add(contact.getId());
            row.add(contact.getName());
            row.add(contact.getPhone());
            row.add(contact.getEmail());
            tableModel.addRow(row);
        }
        System.out.println("GUI: Table populated with " + contacts.size() + " contacts.");
    }

    /**
     * Handles adding a new contact.
     */
    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (contactService.addContact(name, phone, email) != null) {
            JOptionPane.showMessageDialog(this, "Contact added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            populateTable(); // Refresh table to show new contact
            clearFields(); // Clear input fields
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add contact. Check server logs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles updating an existing contact.
     */
    private void updateContact() {
        int selectedId;
        try {
            selectedId = Integer.parseInt(selectedContactIdLabel.getText());
            if (selectedId == 0) { // If it's "None" or some default, prevent update
                 JOptionPane.showMessageDialog(this, "Please select a contact from the table to update.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                 return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please select a contact from the table to update.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }


        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to update contact with ID: " + selectedId + "?",
                "Confirm Update", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (contactService.updateContact(selectedId, name, phone, email)) {
                JOptionPane.showMessageDialog(this, "Contact updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                populateTable(); // Refresh table
                clearFields(); // Clear fields
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update contact. Check server logs.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles deleting a selected contact.
     */
    private void deleteContact() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a contact from the table to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the ID from the selected row in the table model
        int contactId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete contact ID: " + contactId + " (" + tableModel.getValueAt(selectedRow, 1) + ")?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (contactService.deleteContact(contactId)) {
                JOptionPane.showMessageDialog(this, "Contact deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                populateTable(); // Refresh table
                clearFields(); // Clear fields
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete contact. Check server logs.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Clears the input text fields and resets the selected ID label.
     */
    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        selectedContactIdLabel.setText("None");
        contactTable.clearSelection(); // Clear table selection
    }

    /**
     * Main method to run the GUI application.
     * @param args Command line arguments (not used here).
     */
    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Set an attractive look and feel (optional, but good for aesthetics)
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new ContactBookGUI().setVisible(true);
            }
        });
    }
}
