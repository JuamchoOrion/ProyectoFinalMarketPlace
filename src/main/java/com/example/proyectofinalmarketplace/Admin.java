package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.VendedorNoEncontradoException;
import com.example.proyectofinalmarketplace.exceptions.VendedorYaExisteException;

import java.util.List;

public class Admin extends Usuario{

    public List<Vendedor> vendedores;
    public List<Producto> productos;
    public List<Admin> administradores;
    public Marketplace marketplace;

    // Constructor
    public Admin(String nombre,String contrasenia) {
        super(nombre,contrasenia);
    }

    public Vendedor addVendedor(Vendedor vendedor) throws VendedorYaExisteException {
        if (!vendedores.contains(vendedor)) {
            vendedores.add(vendedor);
            return vendedor;
        }
        Utilities.getInstance().logInfo("El vendedor ya existe");
        throw new VendedorYaExisteException("El vendedor ya existe");
    }

    public void removerVendedor(String contrasenia) throws VendedorNoEncontradoException {
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getContrasenia().equals(contrasenia)) {
                vendedores.remove(vendedor);
                System.out.println("Miembro " + vendedor.getNombre() + " con contraseña " + contrasenia + " eliminado correctamente");
            }
        }
        Utilities.getInstance().logInfo("El vendedor no existe");
        throw new VendedorNoEncontradoException("El vendedor no existe");
    }

    public Vendedor editarVendedor(Vendedor vendedor,String newNombre, String newDescripcion, String newContrasenia, String newDireccion, int newCedula) {
        if (vendedor != null) {
            vendedor.setNombre(newNombre);
            vendedor.setDescripcion(newDescripcion);
            vendedor.setDireccion(newDireccion);
            vendedor.setContrasenia(newContrasenia);
            vendedor.setCedula(newCedula);
            return vendedor;
        }
        return null;
    }


}