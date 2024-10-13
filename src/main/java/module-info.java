module com.pavelryzh.lab02 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.pavelryzh.lab02 to javafx.fxml;
    exports com.pavelryzh.lab02;
}