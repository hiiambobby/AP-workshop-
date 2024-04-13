import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Hospital {
    private static HashMap<String, ArrayList<Reserve>> reservations = new HashMap<>();
    private HashMap<String, Person> persons = new HashMap<>();

    public Hospital() {

    }

    private boolean isPerson(String id) {
        if (persons.containsKey(id))
            return true;
        else
            return false;
    }

    public void addPerson(String id) {
        if (isPerson(id)) {
            System.out.println("success");
        } else {
            persons.put(id, null);
        }
    }

    public void showMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("1.sign in as doctor");
        System.out.println("2.sign up as doctor");
        System.out.println("3.sign in as patient");
        System.out.println("4.sign up as patient");
        System.out.println("5.exit");
        int input = scan.nextInt();
        switch (input) {
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("invalid input");
        }

    }

}
