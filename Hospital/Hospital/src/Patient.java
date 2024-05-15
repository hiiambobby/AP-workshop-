package src;

import java.util.Random;
import java.util.Scanner;

public class Patient extends Person {
    public Patient(String username, String password) {
        super(username,password);
    }

    public void menu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("1.See Reservation Table");
        System.out.println("2.Reserve A Doctor");
        System.out.println("3.Charge Account");
        System.out.println("4.Get Dicount Percentage Discount Ticket");
        System.out.println("5.Get Price Based Discount Ticket");
        System.out.println("6.exit");
        int input = scan.nextInt();
        switch (input) {
            case 1:
                break;
            case 2:
                break;
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

    public void getDiscount(int choice){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Date:(1-31):");
        int date = scan.nextInt();
        if(choice == 1)
            Hospital.givePercentageDicount(getUserName(),date);
        else if(choice == 2)
            Hospital.givePriceBasedDiscount(getUserName(),date);

    }
}