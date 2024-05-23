import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import com.sun.beans.decoder.StringElementHandler;

public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        OutputStreamWriter osw = null;
        InputStreamReader isr = null;
        Scanner scanner = null;
        try {
            socket = new Socket("localhost", 12345);
            isr = new InputStreamReader(socket.getInputStream());
            osw = new OutputStreamWriter(socket.getOutputStream());
            scanner = new Scanner(System.in);

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

            osw.writeObject(grid);
            //int[][] solvedGrid = (int[][]) 

            if (solvedGrid != null) {
                try (FileWriter writer = new FileWriter(outputFileName)) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            writer.write(solvedGrid[i][j] + " ");
                        }
                        writer.write("\n");
                    }
                } catch (IOException e) {
                    System.out.println("Error writing to output file!");
                }
            } else {
                System.out.println("No solution exists for the provided Sudoku puzzle.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}