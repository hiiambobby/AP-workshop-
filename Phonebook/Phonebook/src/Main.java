import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scan = new Scanner(System.in);
        Parser parser = new Parser(phoneBook);

        while (true) {
            String input = scan.nextLine();
            parser.parse(input);
            // scan.close();
        }

    }
}
