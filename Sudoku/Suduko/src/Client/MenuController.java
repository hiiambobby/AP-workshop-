package Client;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Button exitButton;

    @FXML
    private Button startButton;

    @FXML
    void exitSelected(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void startSelected(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/difSelect.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) startButton.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}