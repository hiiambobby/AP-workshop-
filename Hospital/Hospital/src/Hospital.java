package src;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Hospital {
    private static HashMap<String, ArrayList<Reserve>> reservations = new HashMap<>();
    private HashMap<String, Person> people = new HashMap<>();

    public Hospital() {

    }

    private boolean isPerson(String id) {
        if (people.containsKey(id))
            return true;
        else
            return false;
    }

    public void addPerson(String id) {
        if (isPerson(id)) {
            System.out.println("success");
        } else {
            people.put(id, null);
        }
    }

    public void showMenu(int input) {
        Scanner scan = new Scanner(System.in);

        switch (input) {
            case 1:
            {
                getPatientInfo();
                break;}
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("invalid input");
        }

    }

    private void getPatientInfo() {
        String username,password;
        System.out.println("username:");
        Scanner scan = new Scanner(System.in);
        username = scan.nextLine();
        System.out.println("password:");
        username = scan.nextLine();
        System.out.println("success");
    }
    public static void givePercentageDicount(int date)
    {
        String id = "";
        int expitarionDate =  date + 7;
        Discount discount = new PercentageDiscount("Per" + randomGeneratorString(id),expitarionDate);

    }
    public static void givePriceBasedDiscount(int date)
    {
        String id = "";
        int expitarionDate = date + 7;
        Discount discount = new PriceBasedDiscount("Price" +randomGeneratorString(id),expitarionDate);

    }
    public static String randomGeneratorString(String ticketId)
    {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 4;
        Random random = new Random();

      ticketId = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return ticketId;
    }

}
