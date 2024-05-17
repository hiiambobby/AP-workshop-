package src;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Doctor extends Person {
    ArrayList<String> availableDays = new ArrayList<>();
    public Doctor(String userId,String passWord)
    {
        super(userId,passWord);
    }
    public void menu() {
        Scanner scan = new Scanner(System.in);
        while (true)
        {
        System.out.println("1.visits");
        System.out.println("2.Fill in attendance Table");
        System.out.println("3.exit");
        try {
            int input = scan.nextInt();
            switch (input) {
                case 1:
                    break;
                case 2:
                {
                    fillAttendanceDays();
                    Hospital.setDoctorsDays(getUserName(),availableDays);
                    break;}
                case 3:
                    Main.menu();
                default: {
                    System.out.println("Something happened. choose again");

                }

            }
        }
        catch (Exception e)
        {
            System.out.println("Enter a valid input!!");
        }
    }}
    public void fillAttendanceDays(){
        Scanner scan = new Scanner(System.in);
        String[] validDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        List<String> validDaysList = List.of(validDays);

        System.out.println("Enter Available Days (From Saturday to Friday) divided by ENTER:");
        System.out.println("Type 'done' or press ENTER without typing anything to finish");

        while (true) {
            String s = scan.nextLine().trim();
            if (s.equalsIgnoreCase("done") || s.isEmpty()) {
                break;
            } else if (validDaysList.contains(s)) {
                availableDays.add(s);
            } else {
                System.out.println("Invalid day. Please enter a valid day from the list:");
            }
        }


        System.out.println("Entered Available Days: " + availableDays);
    }


}
