module com.example.proyectofinalmarketplace {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires jdk.jshell;


    opens com.example.proyectofinalmarketplace to javafx.fxml;
    exports com.example.proyectofinalmarketplace;
}