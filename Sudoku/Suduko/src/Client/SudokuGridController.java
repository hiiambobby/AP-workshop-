package Client;


import java.net.Socket;
import java.io.*;

import java.util.*;
import java.util.function.UnaryOperator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SudokuGridController {

    private final int rank = 9;
    private TextField[][] textFields = new TextField[rank][rank];
    private HashMap<TextField, ArrayList<TextField>> collisions = new HashMap<TextField, ArrayList<TextField>>();

    @FXML
    private GridPane puzzle;

    @FXML
    private Button backButton;

    @FXML
    private Label timerLabel;

    private Timeline timeline;
    private int secondsPassed = 0;

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTimer() {
        secondsPassed++;
        int minutes = secondsPassed / 60;
        int seconds = secondsPassed % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }
    public void init(int difficulty) {
        // Populate the puzzle grid with textFields
        for (int row = 0; row < rank; row++) {
            for (int column = 0; column < rank; column++) {
                final int currentRow = row;
                final int currentColumn = column;

                TextField textField = new TextField();
                addTextFormatter(textField);
                textField.setPrefSize(300, 300);
                puzzle.add(textField, column, row);
                textFields[row][column] = textField;
                textField.setFocusTraversable(true);

                // Textfield traversal controller
                textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.UP && currentRow > 0) {
                        textFields[currentRow - 1][currentColumn].requestFocus();
                    } else if (event.getCode() == KeyCode.DOWN && currentRow < rank - 1) {
                        textFields[currentRow + 1][currentColumn].requestFocus();
                    } else if (event.getCode() == KeyCode.LEFT && currentColumn > 0) {
                        textFields[currentRow][currentColumn - 1].requestFocus();
                    } else if (event.getCode() == KeyCode.RIGHT && currentColumn < rank - 1) {
                        textFields[currentRow][currentColumn + 1].requestFocus();
                    }
                });

                textField.textProperty().addListener((obs, oldText, newText) -> {
                    changeDuplicatesColor(currentRow, currentColumn, oldText, newText);
                });
            }
        }
        // For 3x3 blocks
        addPuzzleStyle();
        createPuzzle(difficulty);
        startTimer();
    }

    private void addPuzzleStyle() {
        for (int i = 0; i < rank; i++) {
            for (int j = 0; j < rank; j++) {
                if (i % 3 != 2 && j % 3 == 2) {
                    textFields[i][j].getStyleClass().add("thick-right-line");
                } else if (i % 3 == 2 && j % 3 != 2) {
                    textFields[i][j].getStyleClass().add("thick-down-line");
                } else if (i % 3 == 2 && j % 3 == 2) {
                    textFields[i][j].getStyleClass().add("thick-right-down-line");
                } else {
                    textFields[i][j].getStyleClass().add("default-line");
                }
            }
        }
    }

    // Creates the sudoku puzzle from file
    private void createPuzzle(int difficulty) {
        //new version
        try(Socket socket = new Socket("localhost", 1234);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)){

            bufferedWriter.write(difficulty);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // Receive the response from the server
            /*String response = bufferReader.readLine();

            if (response.equals("Invalid difficulty level.") || response.equals("Sudoku puzzle file not found.")) {
                System.out.println("Server: " + response);
                //should be replaced with error popUp
                return;  // Exit if there was an error
            }*/

            // receiving sudoku from server
            int[][] unsolvedGrid = new int[9][9];
            for (int i = 0; i < 9; i++) {
                String[] line = bufferReader.readLine().split(" ");
                //String[] line = bufferReader.readLine().split(" ");
                for (int j = 0; j < 9; j++) {
                    unsolvedGrid[i][j] = Integer.parseInt(line[j]);
                }
            }

            Font boldFont = Font.font("Arial", FontWeight.BOLD, 13);
            for (int i = 0; i < rank; i++) {
                for (int j = 0; j < rank; j++) {
                    int readNumber = unsolvedGrid[i][j];
                    if (readNumber != 0) {
                        textFields[i][j].setFont(boldFont);
                        textFields[i][j].setText("" + readNumber);
                        textFields[i][j].setEditable(false);
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeDuplicatesColor(int currentRow, int currentColumn, String oldText, String newText) {
        if (newText.equals(oldText)) {
            return;
        }

        TextField textField = textFields[currentRow][currentColumn];
        if (collisions.containsKey(textField)) {
            Set<TextField> keySet = new HashSet<TextField>();
            keySet.add(textField);
            applyColor(keySet, "black");
            collisions.remove(textField);
        }

        ArrayList<TextField> newCollisions = new ArrayList<TextField>();
        // Each row and column
        if (!newText.equals("")) {
            for (int j = 0; j < rank; j++) {
                if (j != currentColumn && textFields[currentRow][j].getText().equals(newText)) {
                    newCollisions.add(textFields[currentRow][j]);
                }
            }
            for (int i = 0; i < rank; i++) {
                if (i != currentRow && textFields[i][currentColumn].getText().equals(newText)) {
                    newCollisions.add(textFields[i][currentColumn]);
                }
            }
            // To hanlde repetition in 3x3 grids
            int startRow = currentRow - currentRow % 3;
            int startColumn = currentColumn - currentColumn % 3;
            for (int i = startRow; i < startRow + 3; i++) {
                for (int j = startColumn; j < startColumn + 3; j++) {
                    if ((i != currentRow || j != currentColumn) && textFields[i][j].getText().equals(newText)) {
                        newCollisions.add(textFields[i][j]);
                    }
                }
            }
        }

        if (newCollisions.size() > 0) {
            collisions.put(textField, newCollisions);
        }
        applyColor(collisions.keySet(), "red");
        checkSolvedPuzzle();
    }

    private void applyColor(Set<TextField> textFields, String color) {
        color = String.format("-fx-text-fill: %s;", color);
        for (TextField textField : textFields) {
            textField.setStyle(color);
            for (TextField field : collisions.get(textField)) {
                field.setStyle(color);
            }
        }
    }

    private void addTextFormatter(TextField textField) {
        UnaryOperator<Change> textFilter = c -> {
            // To handle single digit 1 to rank
            String regex = String.format("[1-%d]", rank);
            if (c.getText().matches(regex)) {
                c.setRange(0, textField.getText().length());
                return c;
            } else if (c.getText().isEmpty()) {
                // Adds backSpace as removal option
                return c;
            }
            return null;
        };

        TextFormatter<Integer> formatter = new TextFormatter<Integer>(textFilter);
        textField.setTextFormatter(formatter);
    }

    private void checkSolvedPuzzle() {
        if (collisions.size() > 0) {
            return;
        }
        for (int i = 0; i < rank; i++) {
            for (int j = 0; j < rank; j++) {
                if (textFields[i][j].getText().equals("")) {
                    return;
                }
            }
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Victory!");
        alert.setHeaderText(null);
        alert.setContentText("congratulations! You solved the puzzle!");
        alert.showAndWait();
        changeScene("FXML/mainMenu.fxml");
    }

    private Object changeScene(String newScene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(newScene));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("FXML/Style.css").toExternalForm()); // Add this line
            Stage primaryStage = (Stage) backButton.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    void backButtonPressed(ActionEvent event) {
        changeScene("FXML/Difficulty.fxml");
    }
}