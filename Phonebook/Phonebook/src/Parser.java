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
        if (parts.length < 4) {
            System.out.println("Invalid input for contacts command.");
            return;
        }

        String option = parts[1];
        String firstName = parts[2];
        String lastName = parts[3];

        switch (option) {
            case "-a":
                Contact contact = new Contact();
                
                addContact(firstName, lastName);
                System.out.println("Please enter contact's group: ");
                String group = scan.nextLine();
                contact.setGroup(group);
                System.out.println("Please enter contact's email: ");
                String email = scan.nextLine();
                contact.setEmail(email);
                break;
            case "-r":
                //removeContact(firstName, lastName);
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