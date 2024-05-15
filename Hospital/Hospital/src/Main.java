package src;

import java.util.Scanner;

public class Main {
    public static void menu()
    {            Hospital hospital = new Hospital();

        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("1.sign in as doctor");
            System.out.println("2.sign up as doctor");
            System.out.println("3.sign in as patient");
            System.out.println("4.sign up as patient");
            System.out.println("5.exit");
            int input = scan.nextInt();
            hospital.showMenu(input);
        }
    }
    public static void main(String[] args) {

        menu();

    }
}
