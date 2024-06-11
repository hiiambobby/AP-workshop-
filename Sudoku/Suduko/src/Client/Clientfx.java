package Client;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Clientfx extends Application {

   // private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Sudoku Client");

        Label label = new Label("Welcome to Sudoku Client");
        Button startButton = new Button("Start Game");

        startButton.setOnAction(event -> {
            System.out.println("Start Game button clicked!");
        });

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(label, startButton);

        Scene scene = new Scene(vBox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
