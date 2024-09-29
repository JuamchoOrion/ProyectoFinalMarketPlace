package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.ListaContactosLlenaException;
import com.example.proyectofinalmarketplace.exceptions.UsuarioNoExisteException;

import java.util.ArrayList;
import java.util.List;


public class Marketplace {
    private String nombre;
    private Admin admin;
    private List<Producto> productos;
    private List<Usuario> usuarios;
    private Estadisticas estadistica;

    public Marketplace(String nombre) {
        this.nombre = nombre;
        this.usuarios = new ArrayList<Usuario>();
    }

    public Object autenticacionUsuario(String nombre,String contrasenia) throws UsuarioNoExisteException {

            for (Admin admin : admin.administradores) {
                if (admin.getContrasenia().equals(contrasenia) && admin.getNombre().equals(nombre)) {
                    return admin;
                }
            }
            for (Vendedor vendedor : admin.vendedores) {
                if (vendedor.getContrasenia().equals(contrasenia) && vendedor.getNombre().equals(nombre)) {
                    return vendedor;
                }
            }
            Utilities.getInstance().logWarning("El usuario no existe.");
            throw new UsuarioNoExisteException("El");
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public List<Producto> getListaProductos() {
        return productos;
    }
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
