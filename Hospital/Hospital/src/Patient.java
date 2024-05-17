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
            System.out.println("1.See Reservation Table");
            System.out.println("2.Reserve A Doctor");
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
                        break;
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

    public void getDiscount(int choice){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Date:(1-31):");
        PercentageDiscount percen = null ;
        PriceBasedDiscount price = null ;
        int date = scan.nextInt();
        if(choice == 1) {
            Hospital.givePercentageDicount(percen,getUserName(), date);
            System.out.println("Your ticket is:");
            System.out.println(percen.toString());
        }
        else if(choice == 2){
            Hospital.givePriceBasedDiscount(price,getUserName(),date);
        System.out.println("Your ticket is:");
        System.out.println(price.toString());}

    }
    private void chargeAccount(){
        Scanner scan = new Scanner(System.in);
        System.out.printf("Your current account charge: %d\n", getMoney());
        System.out.print("Enter $: ");
        setMoney(getMoney()+scan.nextInt());
        System.out.printf("Your current account charge is: %d\n", getMoney());
    }
    public static void seeReservationTable()
    {
        HashMap<String, ArrayList<String>> doctorDays = Hospital.getDoctorDays();

        if (doctorDays.isEmpty()) {
            System.out.println("No doctors available.");
            return;
        }

        System.out.println("Available Doctors and their Days:");
        for (Map.Entry<String, ArrayList<String>> entry : doctorDays.entrySet()) {
            String doctorId = entry.getKey();
            ArrayList<String> days = entry.getValue();

            System.out.println("Doctor ID: " + doctorId);
            System.out.println("Available Days: " + String.join(", ", days));
            System.out.println();  // Add a blank line for better readability
        }
    }
}