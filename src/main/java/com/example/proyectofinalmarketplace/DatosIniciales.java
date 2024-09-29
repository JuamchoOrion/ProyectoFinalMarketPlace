package com.example.proyectofinalmarketplace;

import java.util.ArrayList;
import java.util.List;

public class DatosIniciales {
    public static DatosIniciales crearDatosIniciales() {
        List<Usuario> usuarios = new ArrayList<>();
        // Administradores existentes
        Admin a1 = new Admin("Laura", "esotilin");
        usuarios.add(a1);
        Admin a2 = new Admin("Juancho", "etesech");
        Admin a3 = new Admin("Maya", "elpepe");
        Marketplace marketplace = new Marketplace("a");
        marketplace.setUsuarios(usuarios);
        marketplace.setNombre("JavaDictos");
        return null;
    }}