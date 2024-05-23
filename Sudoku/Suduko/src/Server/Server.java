package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static int N = 9;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Sudoku server started, waiting for client...");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    System.out.println("Client connected");
                    int[][] grid = (int[][]) ois.readObject();
                    if (solveSudoku(grid, 0, 0)) {
                        oos.writeObject(grid);
                    } else {
                        oos.writeObject(null);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean solveSudoku(int grid[][], int row, int col) {
        if (row == N - 1 && col == N)
            return true;
        if (col == N) {
            row++;
            col = 0;
        }
        if (grid[row][col] != 0)
            return solveSudoku(grid, row, col + 1);

        for (int num = 1; num <= 9; num++) {
            if (isSafe(grid, row, col, num)) {
                grid[row][col] = num;
                if (solveSudoku(grid, row, col + 1))
                    return true;
                grid[row][col] = 0;
            }
        }
        return false;
    }

    static boolean isSafe(int[][] grid, int row, int col, int num) {
        for (int x = 0; x <= 8; x++)
            if (grid[row][x] == num)
                return false;
        for (int x = 0; x <= 8; x++)
            if (grid[x][col] == num)
                return false;

        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i + startRow][j + startCol] == num)
                    return false;

        return true;
    }
}