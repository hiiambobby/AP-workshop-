package Client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Clientfx extends Application {

    private Socket socket;
    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;
    private BufferedReader bufferReader;
    private BufferedWriter bufferedWriter;
    private Scanner scanner;

    private int[][] unsolvedGrid;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku Client");

        Label label = new Label("Welcome to Sudoku Client");
        Button startButton = new Button("Start Game");

        startButton.setOnAction(event -> {
            connectToServer();
            sendAndReceiveData();
            displaySudokuGrid(primaryStage);
            disconnectFromServer();
        });

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(label, startButton);

        Scene scene = new Scene(vBox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 1234);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            scanner = new Scanner(System.in);

            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendAndReceiveData() {
        try {
            // Send difficulty level to the server
            System.out.println("Enter game level (easy, medium, hard):");
            String difficultyLevel = scanner.nextLine();
            bufferedWriter.write(difficultyLevel);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // Receive response from the server
            String response = bufferReader.readLine();

            if (response.equals("Invalid difficulty level.") || response.equals("Sudoku puzzle file not found.")) {
                System.out.println("Server: " + response);
                return;
            }

            // Receive the unsolved Sudoku puzzle from the server
            unsolvedGrid = new int[9][9];
            for (int i = 0; i < 9; i++) {
                String[] line = bufferReader.readLine().split(" ");
                for (int j = 0; j < 9; j++) {
                    unsolvedGrid[i][j] = Integer.parseInt(line[j]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displaySudokuGrid(Stage primaryStage) {
        // Create a grid pane for the Sudoku board
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));

        // Add labels for the Sudoku board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Label cell = new Label(Integer.toString(unsolvedGrid[i][j]));
                cell.setPrefWidth(30);
                cell.setPrefHeight(30);
                cell.setAlignment(Pos.CENTER);
                gridPane.add(cell, j, i);
            }
        }

        // Create a button to submit the solved Sudoku puzzle
        Button submitButton = new Button("Submit Solution");
        submitButton.setOnAction(event -> {
            // Handle submission of the solution
            // For now, just print a message
            System.out.println("Solution submitted!");
        });

        // Create a VBox to hold the grid and the submit button
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(gridPane, submitButton);

        Scene scene = new Scene(vBox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void disconnectFromServer() {
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

    public static void main(String[] args) {
        launch(args);
    }
}
