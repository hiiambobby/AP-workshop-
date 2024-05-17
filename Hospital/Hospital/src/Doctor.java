package src;

import java.util.*;

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
        System.out.println("1.view visits");
        System.out.println("2.Fill in attendance Table"); //done
        System.out.println("3.exit");
        try {
            int input = scan.nextInt();
            switch (input) {
                case 1:
                {viewVisits();
                    break;}
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
    public void viewVisits() {
        HashMap<String, String> reservations = Hospital.getReservations();

       // System.out.println("Reservations for Doctor ID: " + getUserName());
        boolean hasReservations = false;

        for (Map.Entry<String, String> entry : reservations.entrySet()) {
            String patientName = entry.getKey();
            String reservationDetails = entry.getValue();

            if (reservationDetails.contains("Doctor ID: " + getUserName())) {
                hasReservations = true;
                System.out.println("Patient Name: " + patientName + ", " + reservationDetails);
            }
        }

        if (!hasReservations) {
            System.out.println("No reservations found.");
        }
    }
}
