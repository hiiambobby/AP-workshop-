package src;

import java.util.*;

public class Patient extends Person {
    public Patient(String username, String password) {
        super(username,password);
    }

    public void menu() {
        Scanner scan = new Scanner(System.in);
        try{
        while(true) {
            System.out.println("1.See Reservation Table"); //done
            System.out.println("2.Reserve A Doctor"); //done
            System.out.println("3.Charge Account"); //done
            System.out.println("4.Get Percentage Discount Ticket"); //done
            System.out.println("5.Get Price Based Discount Ticket"); //done
            System.out.println("6.exit");
                int input = scan.nextInt();
                switch (input) {
                    case 1:{
                        seeReservationTable();
                        break;}
                    case 2:
                    {
                        reserveDoctor();
                        break;
                        }
                    case 3: {
                        chargeAccount();
                        break;
                    }
                    case 4:
                        getDiscount(1);
                        break;
                    case 5:
                        getDiscount(2);
                        break;
                    case 6:
                        Main.menu();
                }
            }

            }
           catch (Exception e)
        {
            System.out.println("Enter a valid input");
        }

    }

    public void getDiscount(int choice) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Date (1-31):");
        int date = scan.nextInt();

        if (choice == 1) {
            PercentageDiscount percen = Hospital.givePercentageDiscount(getUserName(), date);
            System.out.println("Your ticket is:");
            System.out.println(percen);
        } else if (choice == 2) {
            PriceBasedDiscount price = Hospital.givePriceBasedDiscount(getUserName(), date);
            System.out.println("Your ticket is:");
            System.out.println(price);
        }
    }
    private void chargeAccount(){
        Scanner scan = new Scanner(System.in);
        System.out.printf("Your current account charge: %d\n", getMoney());
        System.out.print("Enter $: ");
        setMoney(getMoney()+scan.nextInt());
        System.out.printf("Your current account charge is: %d\n", getMoney());
    }
    public static void seeReservationTable() {
        if (Hospital.getDoctorDays().isEmpty()) {
            System.out.println("No doctors available.");
            return;
        }

        System.out.println("Available Doctors and their Days:");
        int i = 1; // Starting index from 1 for better readability
        for (Map.Entry<String, ArrayList<String>> entry : Hospital.getDoctorDays().entrySet()) {
            String doctorId = entry.getKey();
            ArrayList<String> days = entry.getValue();

            System.out.println(i + ". Doctor ID: " + doctorId);
            System.out.println("   Available Days: " + String.join(", ", days));
            System.out.println();
            i++;
        }
    }

    public void reserveDoctor() {
        Scanner scan = new Scanner(System.in);
        seeReservationTable();

        System.out.println("Enter the number corresponding to the doctor you want to reserve:");
        int doctorIndex = scan.nextInt();
        scan.nextLine();  // Consume newline

        HashMap<String, ArrayList<String>> doctorDays = Hospital.getDoctorDays();
        String selectedDoctorId = null;
        int i = 1;
        for (String doctorId : doctorDays.keySet()) {
            if (i == doctorIndex) {
                selectedDoctorId = doctorId;
                break;
            }
            i++;
        }

        if (selectedDoctorId == null) {
            System.out.println("Invalid doctor selection.");
            return;
        }

        ArrayList<String> availableDays = doctorDays.get(selectedDoctorId);
        System.out.println("Available days for Doctor ID " + selectedDoctorId + ": " + String.join(", ", availableDays));
        System.out.println("Enter the day you want to reserve:");
        String selectedDay = scan.nextLine();

        if (!availableDays.contains(selectedDay)) {
            System.out.println("Invalid day selection.");
            return;
        }

        Hospital.addReservation(getUserName(), selectedDoctorId, selectedDay);
        System.out.println("Reservation successful for Doctor ID " + selectedDoctorId + " on " + selectedDay + ".");
    }

}