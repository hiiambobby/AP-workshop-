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
        List<Contact> foundContact = new ArrayList<>();

        inputStr = inputStr.toLowerCase();

        for (Contact contact : contacts) {
            if (contactMatchesInput(contact, inputStr)) {
                foundContact.add(contact);
            }
        }

        // Convert List<Contact> to Contact[]
        return foundContact.toArray(new Contact[0]);
    }

    public Contact[] findContacts(String searchWord, String searchType) {
        List<Contact> foundContacts = new ArrayList<>();

        searchWord = searchWord.toLowerCase();

        for (Contact contact : contacts) {
            if (contactMatchesInput(contact, searchWord)) {
                foundContacts.add(contact);
            }
        }

        // Convert List<Contact> to Contact[]
        return foundContacts.toArray(new Contact[0]);
    }

    private boolean contactMatchesInput(Contact contact, String inputStr) {
        String firstName = contact.getFirstName().toLowerCase();
        String lastName = contact.getLastName().toLowerCase();
        String group = contact.getGroup().toLowerCase();
        String email = contact.getEmail().toLowerCase();
        String phoneNumber = contact.getPhoneNumber().toString().toLowerCase();
        String address = contact.getAddress().toString().toLowerCase();

        return firstName.contains(inputStr)
                || lastName.contains(inputStr)
                || group.contains(inputStr)
                || email.contains(inputStr)
                || phoneNumber.contains(inputStr)
                || address.contains(inputStr);
    }

    public void printContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("All contacts:");
            for (Contact contact : contacts) {
                System.out.println("------------------------------");
                System.out.println("First Name: " + contact.getFirstName());
                System.out.println("Last Name: " + contact.getLastName());
                System.out.println("Group: " + contact.getGroup());
                System.out.println("Email: " + contact.getEmail());
                System.out.println("Phone Number: " + contact.getPhoneNumber());
                System.out.println("Address: " + contact.getAddress());
                System.out.println("------------------------------");
            }
        }
    }

    // Getters for contacts and contactCount
    public List<Contact> getContacts() {
        return contacts;
    }

    public int getContactCount() {
        return contactCount;
    }
}
