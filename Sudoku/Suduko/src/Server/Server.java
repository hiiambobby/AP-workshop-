package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

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

                    // Read the difficulty level from the client
                    String difficultyLevel = bufferReader.readLine();
                    String fileName;
                    switch (difficultyLevel.toLowerCase()) {
                        case "easy":
                            fileName = "easy.txt";
                            break;
                        case "medium":
                            fileName = "medium.txt";
                            break;
                        case "hard":
                            fileName = "hard.txt";
                            break;
                        default:
                            bufferedWriter.write("Invalid difficulty level.");
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                            continue;
                    }

                    // Read the unsolved Sudoku puzzle from the file
                    int[][] unsolvedGrid = new int[9][9];
                    try (Scanner fileScanner = new Scanner(new FileReader(fileName))) {
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                if (fileScanner.hasNextInt()) {
                                    unsolvedGrid[i][j] = fileScanner.nextInt();
                                }
                            }
                        }
                    } catch (FileNotFoundException e) {
                        bufferedWriter.write("Sudoku puzzle file not found.");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        continue;
                    }

                    // Send the unsolved Sudoku puzzle to the client
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            bufferedWriter.write(unsolvedGrid[i][j] + " ");
                        }
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.flush();

                    // Receive the solved Sudoku puzzle from the client
                    int[][] solvedGrid = new int[9][9];
                    for (int i = 0; i < 9; i++) {
                        String[] line = bufferReader.readLine().split(" ");
                        for (int j = 0; j < 9; j++) {
                            solvedGrid[i][j] = Integer.parseInt(line[j]);
                        }
                    }

                    // Instantiate SudokuVerifier and verify the grid
                    SudokuVerifier sudokuVerifier = new SudokuVerifier();
                    int result = sudokuVerifier.verify(solvedGrid);

                    // Interpret the result and send an appropriate message back to the client
                    String message;
                    switch (result) {
                        case 0:
                            message = "The Sudoku solution is correct.";
                            break;
                        case -1:
                            message = "The Sudoku board is invalid due to size or number range issues.";
                            break;
                        case -2:
                            message = "The Sudoku board has duplicate numbers in a 3x3 sub-grid.";
                            break;
                        case -3:
                            message = "The Sudoku board has duplicate numbers in a row.";
                            break;
                        case -4:
                            message = "The Sudoku board has duplicate numbers in a column.";
                            break;
                        default:
                            message = "Unknown error.";
                    }

                    bufferedWriter.write(message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   static class SudokuVerifier {

        public static final int ROW_LENGTH = 9;

        public int verify(int[][] candidateSolution) {
            // Check if the board is valid size
            if (!sizeChecker(candidateSolution)) {
                return -1;
            }

            // Check if all numbers are valid (1-9)
            if (!numberRangeChecker(candidateSolution)) {
                return -1;
            }

            // Check Sudoku rules
            return sudokuRuleChecker(candidateSolution);
        }

        // Checks whether the board is 9x9
        private boolean sizeChecker(int[][] board) {
            if (board.length != ROW_LENGTH) {
                return false;
            }
            for (int[] row : board) {
                if (row.length != ROW_LENGTH) {
                    return false;
                }
            }
            return true;
        }

        // Checks if all numbers are between 1 and 9
        private boolean numberRangeChecker(int[][] board) {
            for (int[] row : board) {
                for (int num : row) {
                    if (num < 0 || num > 9) {
                        return false;
                    }
                }
            }
            return true;
        }

        // Checks whether any of the rules are broken (columns, rows, sub-grids).
        private int sudokuRuleChecker(int[][] sudoku) {
            for (int i = 0; i < ROW_LENGTH; i += 3) {
                for (int j = 0; j < ROW_LENGTH; j += 3) {
                    if (duplicateChecker(gridToArrayConverter(i, j, sudoku))) {
                        return -2;
                    }
                }
            }

            for (int loop = 0; loop < ROW_LENGTH; loop++) {
                if (duplicateChecker(sudoku[loop])) {
                    return -3;
                }
            }

            for (int loop = 0; loop < ROW_LENGTH; loop++) {
                int[] arrayToCheck = new int[9];
                for (int loop2 = 0; loop2 < ROW_LENGTH; loop2++) {
                    arrayToCheck[loop2] = sudoku[loop2][loop];
                }
                if (duplicateChecker(arrayToCheck)) {
                    return -4;
                }
            }

            return 0;
        }

        // Checks whether a single dimensional array has any duplicates
        public boolean duplicateChecker(int[] arrayToCheck) {
            boolean[] seen = new boolean[ROW_LENGTH + 1]; // +1 to handle number 9
            for (int number : arrayToCheck) {
                if (number == 0) continue; // Skip empty cells
                if (seen[number]) {
                    return true;
                }
                seen[number] = true;
            }
            return false;
        }

        // Converts two dimensional array to a single dimensional array
        public int[] gridToArrayConverter(int i, int j, int[][] array) {
            int[] result = new int[ROW_LENGTH];
            int resultIndex = 0;
            for (int k = i; k < (i + 3); k++) {
                for (int l = j; l < (j + 3); l++) {
                    result[resultIndex] = array[k][l];
                    resultIndex++;
                }
            }
            return result;
        }

        // Reads the Sudoku board from a file
        public static int[][] readSudokuFromFile(String filename) throws IOException {
            int[][] board = new int[ROW_LENGTH][ROW_LENGTH];
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            int row = 0;

            while ((line = br.readLine()) != null && row < ROW_LENGTH) {
                String[] values = line.split(" ");
                for (int col = 0; col < values.length; col++) {
                    board[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }

            br.close();
            return board;
        }
    }
}