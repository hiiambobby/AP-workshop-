package src;

import java.util.Scanner;
import java.util.ArrayList;

public class Doctor extends Person {
    ArrayList<String> availableDate = new ArrayList<>();
    public Doctor(String userId,String passWord)
    {
        super(userId,passWord);
    }
    public void menu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("1.visits");
        System.out.println("2.Reservation Table");
        int input = scan.nextInt();
        switch (input) {
            case 1:
                break;
            case 2:
                break;
        }
    }

}
