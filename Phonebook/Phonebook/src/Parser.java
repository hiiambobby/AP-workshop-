import java.util.Arrays;
import java.util.Scanner;

public class Parser {

    private PhoneBook phoneBook;

    public Parser(PhoneBook phoneBook) {
        this.phoneBook = phoneBook; // Store the PhoneBook instance
    }

    public void parse(String input) {
        String[] parts = input.split("\\s+");

        if (parts.length < 1) {
            System.out.println("Invalid input.");
            return;
        }

        String command = parts[0];

        switch (command) {
            case "contacts":
                handleContactsCommand(parts);
                break;
            case "show":
                handleShowCommand(parts);
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid command.");
                break;
        }
    }

    private void handleContactsCommand(String[] parts) {
        Scanner scan = new Scanner(System.in);
        if (parts.length < 4) {
            System.out.println("Invalid input for contacts command.");
            return;
        }

        String option = parts[1];
        String firstName = parts[2];
        String lastName = parts[3];

        switch (option) {
            case "-a":
                System.out.println("Please enter contact's group: ");
                String group = scan.nextLine();
                System.out.println("Please enter contact's email: ");
                String email = scan.nextLine();
                System.out.println("Please enter country code: ");
                String countryCode = scan.nextLine();
                System.out.println("Please enter the phone number:");
                String phoneNumber = scan.nextLine();
                System.out.println("Please enter contact's country");
                String country = scan.nextLine();
                System.out.println("Please enter contact's city");
                String city = scan.nextLine();
                System.out.println("Please enter contact's zipcode");
                String zipcode = scan.nextLine();
                // scan.close();

                Address address = new Address(zipcode, country, city);
                PhoneNumber phone = new PhoneNumber(countryCode, phoneNumber);
                Contact contact = new Contact(firstName, lastName, group, email, phone, address);
                if (phoneBook.addContact(contact)) {
                    System.out.println("Contact saved successfully!");
                }
                break;
            case "-r":
                if (phoneBook.deleteContact(firstName, lastName)) {
                    System.out.println("Ok");
                } else
                    System.out.println("not-found");

                break;
            default:
                System.out.println("Invalid option for contacts command.");
                break;
        }
    }

    private void handleShowCommand(String[] parts) {
        String option;
        if (parts.length < 2) {
            option = "";

        } else
            option = parts[1];

        switch (option) {
            case "-g":
                String searchType = parts[2];
                String searchWord = parts[3];
                Contact[] foundContacts1 = phoneBook.findContact(searchWord);
                if (foundContacts1.length == 0) {
                    System.out.println("No contacts found");
                } else {
                    System.out.println("Found contacts:");
                    for (Contact contact : foundContacts1) {
                        // Manually print contact details
                        System.out.println("[" + contact.getFirstName() + contact.getLastName() + "]");
                        // System.out.println("Group: " + contact.getGroup());
                        // System.out.println("Email: " + contact.getEmail());
                        // System.out.println("Phone Number: " + contact.getPhoneNumber());
                        // System.out.println("Address: " + contact.getAddress());
                        System.out.println();
                    }
                }
                break;

            case "-c":// search by firstname or lastname
                String contactInfo = parts[2];
                Contact[] foundContacts = phoneBook.findContact(contactInfo);
                if (foundContacts.length == 0) {
                    System.out.println("No contacts found");
                } else {
                    System.out.println("Found contacts:");
                    for (Contact contact : foundContacts) {
                        // Manually print contact details
                        System.out.println("[" + contact.getFirstName() + contact.getLastName() + "]");
                        // System.out.println("Group: " + contact.getGroup());
                        // System.out.println("Email: " + contact.getEmail());
                        // System.out.println("Phone Number: " + contact.getPhoneNumber());
                        // System.out.println("Address: " + contact.getAddress());
                        System.out.println();
                    }
                }
                break;
            case "":
                phoneBook.printContacts();
                break;
            default:
                System.out.println("Invalid option for show command.");
                break;
        }
    }

}