package src;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Hospital {
    private static HashMap<String, ArrayList<Reserve>> reservations = new HashMap<>();
    private static HashMap<String,ArrayList<String>> doctorDays = new HashMap<>();
    private static HashMap<String, Person> people = new HashMap<>();

    public Hospital() {

    }

    public static void setDoctorsDays(String userId,ArrayList<String> days){
        doctorDays.put(userId,days);

    }

    public static HashMap<String, ArrayList<String>> getDoctorDays() {
        return doctorDays;
    }

    public static HashMap<String, Person> getPeople() {
        return people;
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
            case 1:{
                signInDoctor();
                break;}
            case 2:
            {signUpDoctor();
                break;}
            case 3:
            {
                getPatientInfo();
                break;}
            case 4:
            {                signUpPatient();
                break;}
            case 5:
            { System.exit(0);
                break;}
            default:
                System.out.println("invalid input");
        }

    }

    public static void givePercentageDicount(PercentageDiscount discount,String userName,int date)
    {
        String id = "";
        int expitarionDate =  date + 7;
        discount = new PercentageDiscount("Per" + randomGeneratorString(id),expitarionDate,userName);

    }
    public static void givePriceBasedDiscount(PriceBasedDiscount discount,String userName,int date)
    {
        String id = "";
        int expitarionDate = date + 7;
        discount = new PriceBasedDiscount("Price" +randomGeneratorString(id),expitarionDate,userName);

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
    private boolean isInList(String password, String username){
        if(people.containsKey(username))
            return people.get(username).getPassWord().equals(password);
        return false;
    }
    private void signInDoctor(){
        String username,password;
        System.out.println("username:");
        Scanner scan = new Scanner(System.in);
        username = scan.nextLine();
        System.out.println("password:");
        password = scan.nextLine();
        //System.out.println("success");
        Person doctor = new Doctor(username,password);
        while(!isInList(doctor.getPassWord(), doctor.getUserName())){
            System.out.println("Wrong username or Password. Enter 'm' to go back to main menu or 'c' to continue: ");
            switch(scan.nextLine().toLowerCase()){
                case "c":
                    break;
                case "m":
                    return;
                default:
                    return;
            }
            System.out.println("Enter Username: ");
            doctor.setUserName(scan.nextLine());
            System.out.println("Enter Password: ");
            doctor.setPassWord(scan.nextLine());
        }
        System.out.println("Signed in successfully. Welcome!");
        ((Doctor) doctor).menu();
    }

    private void signUpDoctor(){
        Scanner scan = new Scanner(System.in);
        String userId,password;
        System.out.println("Enter Username: ");
        userId = scan.nextLine();
        System.out.println("Enter Password: ");
        password = scan.nextLine();
        Person doctor = new Doctor(userId,password);
        while(people.containsKey(doctor.getUserName())){
            System.out.println("This Username is already taken. Choose another one. Enter 'm' to go back to main menu or 'c' to continue: ");
            switch(scan.nextLine().toLowerCase()){
                case "c":
                    break;
                case "m":
                    return;
                default:
                    return;
            }
            System.out.println("");
            System.out.println("Enter Username: ");
            doctor.setUserName(scan.nextLine());
            System.out.println("Enter Password: ");
            doctor.setPassWord(scan.nextLine());
        }
        people.put(doctor.getUserName(), doctor);
        System.out.println("Sign up completed successfully. sign in to continue.");
    }
    private void getPatientInfo() {
        String username, password;
        System.out.println("username:");
        Scanner scan = new Scanner(System.in);
        username = scan.nextLine();
        System.out.println("password:");
        password = scan.nextLine();
        //System.out.println("success");
        Patient patient = new Patient(username, password);
        while (!isInList(patient.getPassWord(), patient.getUserName())) {
            System.out.println("Wrong username or Password. Enter 'm' to go back to main menu or 'c' to continue: ");
            switch (scan.nextLine().toLowerCase()) {
                case "c":
                    break;
                case "m":
                    return;
                default:
                    return;
            }
            System.out.println("");
            System.out.println("Enter Username: ");
            patient.setUserName(scan.nextLine());
            System.out.println("Enter Password: ");
            patient.setPassWord(scan.nextLine());
        }
        System.out.println("Signed in successfully. Welcome!");
        while (true) {
            patient.menu();
        }
    }
    private void signUpPatient(){
        String username, password;
        System.out.println("username:");
        Scanner scan = new Scanner(System.in);
        username = scan.nextLine();
        System.out.println("password:");
        password = scan.nextLine();
        //System.out.println("success");
        Patient patient = new Patient(username, password);
        while(people.containsKey(patient.getUserName())){
            System.out.println("This Username is already taken. Choose another one. Enter 'm' to go back to main menu or 'c' to continue: ");
            switch(scan.nextLine().toLowerCase()){
                case "c":
                    break;
                case "m":
                    return;
                default:
                    return;
            }
        }
        people.put(patient.getUserName(), patient);
        System.out.println("Sign up completed successfully. Sign in to continue.");
    }
}
