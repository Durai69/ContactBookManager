package com.contactbook.service;

import com.contactbook.database.DatabaseManager;
import com.contactbook.model.Contact;

import java.util.ArrayList;
import java.util.Collections; // For unmodifiable list
import java.util.List;

public class ContactService {

    private List<Contact> contacts; // The in-memory ArrayList to hold contacts
    private DatabaseManager dbManager; // Instance of our DatabaseManager

    public ContactService() {
        this.dbManager = new DatabaseManager();
        this.contacts = new ArrayList<>();
        loadContactsFromDatabase(); // Load existing contacts on service startup
    }

    /**
     * Loads all contacts from the database into the in-memory ArrayList.
     * This method is called once when the ContactService is initialized.
     */
    private void loadContactsFromDatabase() {
        contacts = dbManager.getAllContacts();
        System.out.println("ContactService: Loaded " + contacts.size() + " contacts from database.");
    }

    /**
     * Retrieves all contacts currently managed by the service (from in-memory list).
     * @return An unmodifiable List of Contact objects to prevent external direct modification.
     */
    public List<Contact> getAllContacts() {
        // Return a new ArrayList containing all contacts to ensure modifications
        // to the returned list do not affect the internal 'contacts' list directly.
        return new ArrayList<>(contacts);
    }

    /**
     * Adds a new contact. This operation involves both the database and the in-memory list.
     * @param name The name of the new contact.
     * @param phone The phone number of the new contact.
     * @param email The email of the new contact.
     * @return The newly added Contact object with its database-generated ID, or null if the addition failed.
     */
    public Contact addContact(String name, String phone, String email) {
        Contact newContact = new Contact(name, phone, email);
        if (dbManager.addContact(newContact)) { // Attempt to add to database
            // If DB addition successful, add to in-memory list (newContact now has its DB ID)
            contacts.add(newContact);
            System.out.println("ContactService: Contact '" + newContact.getName() + "' added to service and DB.");
            return newContact;
        }
        System.err.println("ContactService: Failed to add contact '" + name + "' to database.");
        return null; // Failed to add to database
    }

    /**
     * Updates an existing contact. This operation updates both the database and the in-memory list.
     * @param contactId The ID of the contact to update.
     * @param newName The new name for the contact.
     * @param newPhone The new phone number for the contact.
     * @param newEmail The new email for the contact.
     * @return true if the contact was updated successfully, false otherwise.
     */
    public boolean updateContact(int contactId, String newName, String newPhone, String newEmail) {
        Contact contactToUpdate = null;
        int contactIndex = -1;

        // Find the contact in the in-memory list by its ID
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == contactId) {
                contactToUpdate = contacts.get(i);
                contactIndex = i;
                break;
            }
        }

        if (contactToUpdate != null) {
            // Create a temporary Contact object with the updated details for DB operation
            // This is important because the 'contactToUpdate' object might be referenced elsewhere.
            Contact tempContactForDBUpdate = new Contact(contactId, newName, newPhone, newEmail);

            if (dbManager.updateContact(tempContactForDBUpdate)) { // Update in database
                // If DB update successful, update the in-memory Contact object directly
                contactToUpdate.setName(newName);
                contactToUpdate.setPhone(newPhone);
                contactToUpdate.setEmail(newEmail);
                System.out.println("ContactService: Contact with ID " + contactId + " updated in service and DB.");
                return true;
            } else {
                System.err.println("ContactService: Failed to update contact with ID " + contactId + " in database.");
            }
        } else {
            System.err.println("ContactService: Contact with ID " + contactId + " not found in memory for update.");
        }
        return false; // Contact not found or failed to update
    }

    /**
     * Deletes a contact. This operation deletes from both the database and the in-memory list.
     * @param contactId The ID of the contact to delete.
     * @return true if the contact was deleted successfully, false otherwise.
     */
    public boolean deleteContact(int contactId) {
        if (dbManager.deleteContact(contactId)) { // Attempt to delete from database
            // If DB deletion successful, remove from in-memory list
            boolean removed = contacts.removeIf(c -> c.getId() == contactId);
            if (removed) {
                System.out.println("ContactService: Contact with ID " + contactId + " deleted from service and DB.");
                return true;
            } else {
                System.err.println("ContactService: Contact with ID " + contactId + " deleted from DB but not found in memory (should not happen).");
                return false; // This implies a logical inconsistency
            }
        }
        System.err.println("ContactService: Failed to delete contact with ID " + contactId + " from database.");
        return false; // Failed to delete from database
    }
}
