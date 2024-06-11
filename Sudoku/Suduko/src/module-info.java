module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens Client to javafx.fxml;
    exports Client;
}