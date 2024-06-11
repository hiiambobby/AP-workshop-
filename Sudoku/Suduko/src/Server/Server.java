package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    static int N = 9;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Sudoku server started, waiting for client...");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        BufferedReader bufferReader = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));
                        BufferedWriter bufferedWriter = new BufferedWriter(
                                new OutputStreamWriter(clientSocket.getOutputStream()))) {

                    System.out.println("Client connected");

                    // getting sudoku info
                    int level = bufferReader.read();
                    String inputFileName;
                    int[][] unsolvedGrid = new int[9][9];
                    switch (level){
                        case 1:
                            inputFileName = "easy.txt";
                            System.out.println("level received");
                            break;
                        case 2:
                            inputFileName = "medium.txt";
                            System.out.println("level received");
                            break;
                        case 3:
                            inputFileName = "hard.txt";
                            System.out.println("level received");
                            break;
                        default:
                            inputFileName = "";
                            System.out.println("level not received");
                    }
                    try (Scanner fileScanner = new Scanner(new FileReader(inputFileName))) {
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
                    }
                    // sending sudoku
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            bufferedWriter.write(unsolvedGrid[i][j] + " ");
                        }
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.flush();
                    //

                    int[][] grid = new int[9][9];
                    for (int i = 0; i < 9; i++) {
                        String[] line = bufferReader.readLine().split(" ");
                        for (int j = 0; j < 9; j++) {
                            grid[i][j] = Integer.parseInt(line[j]);
                        }
                    }

                    if (isValidSudoku(grid)) {
                        bufferedWriter.write("The Sudoku solution is correct.");
                    } else {
                        bufferedWriter.write("The Sudoku solution is incorrect.");
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean isValidSudoku(int[][] grid) {
        // Check each row
        for (int i = 0; i < 9; i++) {
            boolean[] seen = new boolean[9];
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0) {
                    if (seen[grid[i][j] - 1])
                        return false;
                    seen[grid[i][j] - 1] = true;
                }
            }
        }

        // Check each column
        for (int j = 0; j < 9; j++) {
            boolean[] seen = new boolean[9];
            for (int i = 0; i < 9; i++) {
                if (grid[i][j] != 0) {
                    if (seen[grid[i][j] - 1])
                        return false;
                    seen[grid[i][j] - 1] = true;
                }
            }
        }

        // Check each 3x3 subgrid
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                boolean[] seen = new boolean[9];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (grid[row + i][col + j] != 0) {
                            if (seen[grid[row + i][col + j] - 1])
                                return false;
                            seen[grid[row + i][col + j] - 1] = true;
                        }
                    }
                }
            }
        }

        return true;
    }
}