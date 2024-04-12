import java.util.Scanner;
public class Main {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        Parser parser = new Parser();

        while(true) {
            String input = scan.nextLine();
            parser.parse(input);


        }    }
}
