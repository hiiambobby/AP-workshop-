package Client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SudokuGridController {

    private final int rank = 9;
    private TextField[][] textFields = new TextField[rank][rank];
    private HashMap<TextField, ArrayList<TextField>> collisions = new HashMap<TextField, ArrayList<TextField>>();

    @FXML
    private GridPane puzzle;

    @FXML
    private Button backButton;

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
        String fileName = "";
        switch (difficulty) {
            case 0:
                fileName = "easy";
                break;
            case 1:
                fileName = "medium";
                break;
            case 2:
                fileName = "hard";
                break;
        }
        fileName = String.format("Tables/%s.txt", fileName);
        Font boldFont = Font.font("Arial", FontWeight.BOLD, 13);
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int readNumber;
            for (int i = 0; i < rank; i++) {
                for (int j = 0; j < rank; j++) {
                    readNumber = scanner.nextInt();
                    if (readNumber != 0) {
                        textFields[i][j].setFont(boldFont);
                        textFields[i][j].setText("" + readNumber);
                        textFields[i][j].setEditable(false);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
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

        ArrayList<TextField> newColissions = new ArrayList<TextField>();
        // Each row and column
        if (!newText.equals("")) {
            for (int j = 0; j < rank; j++) {
                if (j != currentColumn && textFields[currentRow][j].getText().equals(newText)) {
                    newColissions.add(textFields[currentRow][j]);
                }
            }
            for (int i = 0; i < rank; i++) {
                if (i != currentRow && textFields[i][currentColumn].getText().equals(newText)) {
                    newColissions.add(textFields[i][currentColumn]);
                }
            }
            // To hanlde repetition in 3x3 grids
            int startRow = currentRow - currentRow % 3;
            int startColumn = currentColumn - currentColumn % 3;
            for (int i = startRow; i < startRow + 3; i++) {
                for (int j = startColumn; j < startColumn + 3; j++) {
                    if ((i != currentRow || j != currentColumn) && textFields[i][j].getText().equals(newText)) {
                        newColissions.add(textFields[i][j]);
                    }
                }
            }
        }

        if (newColissions.size() > 0) {
            collisions.put(textField, newColissions);
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