package com.example.proyectofinalmarketplace.exceptions;

public class ProductoYaExisteException extends Exception {
    public ProductoYaExisteException(String mensaje) {
        super(mensaje);
    }
}
