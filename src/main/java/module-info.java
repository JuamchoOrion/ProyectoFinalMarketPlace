module com.example.proyectofinalmarketplace {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;


    opens com.example.proyectofinalmarketplace to javafx.fxml;
    exports com.example.proyectofinalmarketplace;
}