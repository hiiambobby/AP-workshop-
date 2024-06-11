package Client;

import java.io.IOException;

import Client.SudokuGridController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.Node;


public class DifController {

    @FXML
    private Button backButton;

    @FXML
    private Button easyButton;

    @FXML
    private Button hardButton;

    @FXML
    private Button mediumButton;

    @FXML
    void backButtonPressed(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/mainMenu.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("FXML/Style.css").toExternalForm()); // Add this line
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void easyButtonPressed(ActionEvent event) {
        changeScene("FXML/sudokuGrid.fxml", 0);
    }

    @FXML
    void mediumButtonPressed(ActionEvent event) {
        changeScene("FXML/sudokuGrid.fxml", 1);
    }

    @FXML
    void hardButtonPressed(ActionEvent event) {
        changeScene("FXML/sudokuGrid.fxml", 2);
    }

    private void changeScene(String fxmlFile, int difficulty) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) easyButton.getScene().getWindow();
            primaryStage.setScene(scene);

            // Get the controller instance and initialize if necessary
            if (fxmlFile.equals("FXML/sudokuGrid.fxml")) {
                SudokuGridController controller = loader.getController();
                controller.init(difficulty);
            }

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
