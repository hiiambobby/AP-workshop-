package Client;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Label header;

    @FXML
    private Button exitButton;

    @FXML
    private Button startButton;

    @FXML
    void exitSelected(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void startSelected(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/Difficulty.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("FXML/Style.css").toExternalForm()); // Add this line
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}