package com.example.proyectofinalmarketplace;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CrearProductoController {
    @FXML
    private TextField inputNombre;
    @FXML
    private TextField inputImagen;
    @FXML
    private TextField inputPrecio;
    @FXML
    private Button volverButton;
    @FXML
    private Button crear;
    @FXML
    private ComboBox selectCatego;

    @FXML
    public void initialize() {
    }
}
