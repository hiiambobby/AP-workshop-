import java.util.Scanner;

public class Parser {

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
                //handleShowCommand(parts);
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
        PhoneBook phoneBook = new PhoneBook();
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
                System.out.println("Please enter the 12-digit phone number:");
                String phoneNumber = scan.nextLine();
                System.out.println("Please enter contact's country");
                String country = scan.nextLine();
                System.out.println("Please enter contact's city");
                String city = scan.nextLine();
                System.out.println("Please enter contact's zipcode");
                String zipcode = scan.nextLine();

                Address address = new Address(zipcode,country,city);
                PhoneNumber phone = new PhoneNumber(countryCode, phoneNumber);
                Contact contact = new Contact(firstName, lastName, group, email, phone,address);
                if (phoneBook.addContact(contact)) {
                    System.out.println("Contact saved successfully!");}
                break;
            case "-r":
                if(phoneBook.deleteContact(firstName,lastName))
                {
                    System.out.println("Ok");
                }
                else
                    System.out.println("not-found");

                break;
            default:
                System.out.println("Invalid option for contacts command.");
                break;
        }
    }

    private void addContact(String firstName, String lastName) {
        System.out.println("Adding contact: " + firstName + " " + lastName);
        // Add your logic to handle adding a contact here
    }

    private void removeContact(String firstName, String lastName) {
        System.out.println("Removing contact: " + firstName + " " + lastName);
        // Add your logic to handle removing a contact here
    }


}