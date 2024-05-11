import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

// Java program for above approach
public class Main {

    // N is the size of the 2D matrix N*N
    static int N = 9;

    /* Takes a partially filled-in grid and attempts
    to assign values to all unassigned locations in
    such a way to meet the requirements for
    Sudoku solution (non-duplication across rows,
    columns, and boxes) */
    static boolean solveSudoku(int grid[][], int row,
                               int col)
    {

       /*if we have reached the 8th
       row and 9th column (0
       indexed matrix) ,
       we are returning true to avoid further
       backtracking    */
        if (row == N - 1 && col == N)
            return true;

        // Check if column value becomes 9 ,
        // we move to next row
        // and column start from 0
        if (col == N) {
            row++;
            col = 0;
        }

        // Check if the current position
        // of the grid already
        // contains value >0, we iterate
        // for next column
        if (grid[row][col] != 0)
            return solveSudoku(grid, row, col + 1);

        for (int num = 1; num < 10; num++) {

            // Check if it is safe to place
            // the num (1-9) in the
            // given row ,col ->we move to next column
            if (isSafe(grid, row, col, num)) {

             /* assigning the num in the current
             (row,col) position of the grid and
             assuming our assigned num in the position
             is correct */
                grid[row][col] = num;

                // Checking for next
                // possibility with next column
                if (solveSudoku(grid, row, col + 1))
                    return true;
            }
          /* removing the assigned num , since our
          assumption was wrong , and we go for next
          assumption with diff num value */
            grid[row][col] = 0;
        }
        return false;
    }

    /* A utility function to print grid */
    static void print(int[][] grid)
    {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(grid[i][j] + " ");
            System.out.println();
        }
    }

    // Check whether it will be legal
    // to assign num to the
    // given row, col
    static boolean isSafe(int[][] grid, int row, int col,
                          int num)
    {

        // Check if we find the same num
        // in the similar row , we
        // return false
        for (int x = 0; x <= 8; x++)
            if (grid[row][x] == num)
                return false;

        // Check if we find the same num
        // in the similar column ,
        // we return false
        for (int x = 0; x <= 8; x++)
            if (grid[x][col] == num)
                return false;

        // Check if we find the same num
        // in the particular 3*3
        // matrix, we return false
        int startRow = row - row % 3, startCol
                = col - col % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i + startRow][j + startCol] == num)
                    return false;

        return true;
    }

    // Driver Code
    public static void main(String[] args) throws Exception
    {
        FileReader fileIn = null;
        FileWriter output = null;
        Scanner scanner =null;

        try {
            System.out.println("Enter input file name : \"inputname.txt\"");
            scanner = new Scanner(System.in);
            String FileInput = scanner.nextLine();
            System.out.println("Enter input file name : \"outputname.txt\"");
            String outPutFile = scanner.nextLine();
            fileIn = new FileReader(
                    FileInput);
            scanner = new Scanner(fileIn);

            output = new FileWriter(outPutFile);

            int[][] grid = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (scanner.hasNext())
                        grid[i][j] = scanner.nextInt();
                }
            }
            if (solveSudoku(grid, 0, 0))
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (scanner.hasNext())
                            output.write(Integer.toString(grid[i][j]) + " ");
                    }
                    output.write("\n");
                }
            else
                System.out.println("No Solution exists");
        }
        catch(FileNotFoundException e) //read error
        {
            System.out.println("please enter a valid file name!!!");
        }
        catch(IOException e) //write error
        {
            System.out.println("error in output file!!!");
        }
        catch(NullPointerException e)
        {
            System.out.println("there is nothing in ur file");
        }
        catch(InputMismatchException e)
        {
            System.out.println("input file is in a wrong format!");
        }
        finally
        {
            if(output!= null) {
                output.close();
            if(scanner != null)
                scanner.close();
            if(fileIn != null)
                fileIn.close();

            }

    }}
}
