package com.example.proyectofinalmarketplace;
import com.example.proyectofinalmarketplace.exceptions.ListaContactosLlenaException;
import com.example.proyectofinalmarketplace.exceptions.ProductoNoEncontradoException;
import com.example.proyectofinalmarketplace.exceptions.ProductoYaExistenteException;
import com.example.proyectofinalmarketplace.exceptions.ProductoInvalidoException;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

public class Vendedor extends Usuario {

    private int cedula;
    private String descripcion;
    private String direccion;
    private List<Vendedor> listaContactos;
    private List<Producto> listaProductos;

    public Vendedor(String nombre, String descripcion, String contrasenia, String direccion) {
        super(nombre, contrasenia);
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.listaContactos = new ArrayList<>();
        this.listaProductos = new ArrayList<>();
    }


    public String getDescripcion() {
        return descripcion;

    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;

    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;

    }

    public List<Vendedor> getListaContactos() {
        return listaContactos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;

    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    public void agregarContacto(Vendedor vendedor) throws ListaContactosLlenaException, Exception {
        if (listaContactos.contains(vendedor)) {
            Utilities.getInstance().logWarning("El contacto ya existe: " + vendedor.getNombre());
            throw new Exception("El contacto ya existe en la lista.");
        }
        if (listaContactos.size() < 10) {
            listaContactos.add(vendedor);
            Utilities.getInstance().logInfo("Contacto agregado: " + vendedor.getNombre());
        } else {
            Utilities.getInstance().logSevere("Lista de contactos llena. No se puede agregar: " + vendedor.getNombre());
            throw new ListaContactosLlenaException("No se pueden agregar más contactos. La lista ya tiene 10 contactos.");
        }
    }
    //crea un producto
    public void agregarProducto(Producto producto) throws ProductoInvalidoException, ProductoYaExistenteException {
        if (producto == null || producto.getCodigo() == null || producto.getCodigo().isEmpty()) {
            Utilities.getInstance().logSevere("Intento de agregar un producto inválido.");
            throw new ProductoInvalidoException("El producto es inválido o el código es incorrecto.");
        }
        if (listaProductos.contains(producto)) {
            Utilities.getInstance().logWarning("El producto ya existe en la lista: " + producto.getCodigo());
            throw new ProductoYaExistenteException("El producto ya existe en la lista.");
        }
        listaProductos.add(producto);
        Utilities.getInstance().logInfo("Producto agregado correctamente: " + producto.getNombre());
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void eliminarProducto(Producto producto) {
        if (listaProductos.remove(producto)) {
            Utilities.getInstance().logInfo("Producto eliminado: " + producto.getNombre());
        } else {
            Utilities.getInstance().logWarning("Intento de eliminar un producto que no existe: " + producto.getCodigo());
        }
    }
    //metodo para encontar un producto segun su codigo
    public Producto encontrarProductoPorCodigo(String codigo) throws ProductoNoEncontradoException {
        for (Producto producto : listaProductos) {
            if (producto.getCodigo().equals(codigo)) {
                Utilities.getInstance().logInfo("Producto encontrado: " + producto.getNombre());
                return producto;
            }
        }
        Utilities.getInstance().logInfo("No se encontró el producto con el código: " + codigo);
        throw new ProductoNoEncontradoException("No se encontró el producto con el código: " + codigo);
    }

// metodo para actualizar atributos del producto segun id
public void actualizarProducto(String codigo, String nombre, String imagen, String precio,
                               LocalDate fechaPublicacion, Categoria categoria, int likes, Estado estado)
        throws ProductoNoEncontradoException {
    Producto producto = encontrarProductoPorCodigo(codigo);
    producto.setNombre(nombre);
    producto.setImagen(imagen);
    producto.setPrecio(precio);
    producto.setFechaPublicacion(fechaPublicacion);
    producto.setCategoria(categoria);
    producto.setLikes(likes);
    producto.setEstado(estado);
    Utilities.getInstance().logInfo("Producto actualizado: " + producto.getNombre());
}


}
