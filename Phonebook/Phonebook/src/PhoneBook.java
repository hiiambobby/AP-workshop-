import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PhoneBook {
    private List<Contact> contacts;
    private int contactCount;

    public PhoneBook() {
        this.contacts = new ArrayList<>();
        this.contactCount = 0;
    }

    public boolean addContact(Contact contact) {
        boolean added = contacts.add(contact);
        if (added) {
            contactCount++;
            return true;
        }
        return false;
    }

    public boolean deleteContact(String firstName, String lastName) {
        Iterator<Contact> contactsItr = contacts.iterator();
        while (contactsItr.hasNext()) {
            Contact contact = contactsItr.next();
            if (contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName)) {
                contactsItr.remove(); // Remove the matching contact
                contactCount--;
                return true; // if the contact is removed
            }
        }
        return false; // Return false if no matching contact was found
    }

    public Contact[] findContact(String inputStr) {
        List<Contact> foundContacts = new ArrayList<>();

        inputStr = inputStr.toLowerCase();

        for (Contact contact : contacts) {
            if (contactMatchesInput(contact, inputStr)) {
                foundContacts.add(contact);
            }
        }

        // Convert List<Contact> to Contact[]
        return foundContacts.toArray(new Contact[0]);
    }

    private boolean contactMatchesInput(Contact contact, String inputStr) {
        // Convert all fields to lowercase for case-insensitive search
        String firstName = contact.getFirstName().toLowerCase();
        String lastName = contact.getLastName().toLowerCase();

        return firstName.contains(inputStr)
                || lastName.contains(inputStr);
    }

    // Getters for contacts and contactCount
    public List<Contact> getContacts() {
        return contacts;
    }

    public int getContactCount() {
        return contactCount;
    }
}
