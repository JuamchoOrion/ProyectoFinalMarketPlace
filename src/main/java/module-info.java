module com.example.proyectofinalmarketplace {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyectofinalmarketplace to javafx.fxml;
    exports com.example.proyectofinalmarketplace;
}