import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            socket = new Socket("LocalHost", 1234);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            Scanner scanner = new Scanner(System.in);

            while (true) {

                System.out.println("Enter input file name: \"inputname.txt\"");
                String inputFileName = scanner.nextLine();
                System.out.println("Enter output file name: \"outputname.txt\"");
                String outputFileName = scanner.nextLine();
                int[][] grid = new int[9][9];
                try (Scanner fileScanner = new Scanner(new FileReader(inputFileName))) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (fileScanner.hasNextInt()) {
                                grid[i][j] = fileScanner.nextInt();
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Input file not found!");
                    return;
                }

                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                System.out.println("System: " + bufferReader.readLine());

                if (message.equalsIgnoreCase("fuck")) {
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();

                if (inputStreamReader != null)
                    inputStreamReader.close();

                if (outputStreamWriter != null)
                    outputStreamWriter.close();

                if (bufferReader != null)
                    bufferReader.close();

                if (bufferedWriter != null)
                    bufferedWriter.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }

    }
}
