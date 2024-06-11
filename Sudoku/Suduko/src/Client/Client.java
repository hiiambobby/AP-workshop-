package Client;

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

            //
            //System.out.println("Enter input file name: \"inputname.txt\"");
            //String inputFileName = scanner.nextLine();
            //int[][] unsolvedGrid = new int[9][9];
            //

            // getting sudoku from server
            System.out.println("choose difficulty level: 1.easy 2.medium 3.hard");
            String level = scanner.nextLine();
            if (!level.equals("1") && !level.equals("2") && !level.equals("3")){
                System.out.println("wrong input");
                return;}
            bufferedWriter.write(Integer.parseInt(level));
            bufferedWriter.flush();

            // receiving sudoku from server
            int[][] unsolvedGrid = new int[9][9];
            for (int i = 0; i < 9; i++) {
                String[] line = bufferReader.readLine().split(" ");
                for (int j = 0; j < 9; j++) {
                    unsolvedGrid[i][j] = Integer.parseInt(line[j]);
                }
            }

            // Read the unsolved Sudoku puzzle from the file
            /*try (Scanner fileScanner = new Scanner(new FileReader(inputFileName))) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (fileScanner.hasNextInt()) {
                            unsolvedGrid[i][j] = fileScanner.nextInt();
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Input file not found!");
                return;
            }*/



            // Display the unsolved Sudoku puzzle to the user
            System.out.println("Unsolved Sudoku:");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(unsolvedGrid[i][j] + " ");
                }
                System.out.println();
            }

            // User inputs the solved Sudoku puzzle
            int[][] solvedGrid = new int[9][9];
            System.out.println("Enter the solved Sudoku puzzle:");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    solvedGrid[i][j] = scanner.nextInt();
                }
            }

            // Send the solved Sudoku puzzle to the server
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    bufferedWriter.write(solvedGrid[i][j] + " ");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();

            // Receive the validation result from the server
            String response = bufferReader.readLine();
            System.out.println("Server: " + response);

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