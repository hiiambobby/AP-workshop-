module Client {
    requires javafx.controls;
    requires javafx.fxml;

    opens Client to javafx.fxml;
    exports Client;
}