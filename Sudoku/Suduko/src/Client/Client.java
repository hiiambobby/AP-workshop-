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
            socket = new Socket("localhost", 1234);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter game level (easy, medium, hard):");
            int difficultyLevel = scanner.nextInt();
            bufferedWriter.write(difficultyLevel);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // Receive the response from the server
            String response = bufferReader.readLine();

            if (response.equals("Invalid difficulty level.") || response.equals("Sudoku puzzle file not found.")) {
                System.out.println("Server: " + response);
                return;  // Exit if there was an error
            }

            // Receive the unsolved Sudoku puzzle from the server
            int[][] unsolvedGrid = new int[9][9];
            for (int i = 0; i < 9; i++) {
                String temp = bufferReader.readLine();
                System.out.println(temp);
                String[] line = temp.split(" ");
                for (int j = 0; j < 9; j++) {
                    unsolvedGrid[i][j] = Integer.parseInt(line[j]);
                }
            }



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
            StringBuilder solvedLineToSend = new StringBuilder();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    solvedLineToSend.append(solvedGrid[i][j]).append(" ");
                }
            }
            bufferedWriter.write(solvedLineToSend.toString().trim());
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // Receive the validation result from the server
            response = bufferReader.readLine();
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
