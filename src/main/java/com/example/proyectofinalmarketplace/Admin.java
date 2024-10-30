package com.example.proyectofinalmarketplace;

import com.example.proyectofinalmarketplace.exceptions.ProductoYaExisteException;
import com.example.proyectofinalmarketplace.exceptions.VendedorNoEncontradoException;
import com.example.proyectofinalmarketplace.exceptions.UsuarioYaExisteException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Admin extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L; // Asegúrate de actualizar esto si cambias la clase

    transient Utilities logger = Utilities.getInstance(); // Si Utilities no es serializable

    // Constructor
    public Admin(String nombre, String contrasenia) {
        super(nombre, contrasenia);
    }

    public void addVendedor(Marketplace marketplace, Vendedor vendedor) throws UsuarioYaExisteException {
        if (!marketplace.getListaVendedores().contains(vendedor)) {
            marketplace.getListaVendedores().add(vendedor);
            marketplace.getUsuarios().add(vendedor);
            logger.logInfo("El admin " + marketplace.getUsuarioActual().getNombre() + " creó el usuario " + vendedor.getNombre() + " satisfactoriamente");
            return;
        }

        logger.logInfo("El admin " + marketplace.getUsuarioActual().getNombre() + " intentó crear un vendedor, pero ya existe");
        throw new UsuarioYaExisteException("El vendedor ya existe");
    }

    public void crearCategoria(Marketplace marketplace, Categoria categoria) {
        if (!marketplace.getListaCategorias().contains(categoria)) {
            marketplace.getListaCategorias().add(categoria);
            logger.logInfo("El admin " + marketplace.getUsuarioActual().getNombre() + " creó la categoría " + categoria.getNombre() + " satisfactoriamente");
            return;
        }
        logger.logInfo("El admin " + marketplace.getUsuarioActual().getNombre() + " intentó crear una categoría, pero ya existe");
    }

    public void removerProducto(Marketplace marketplace, Vendedor vendedor, Producto producto) throws VendedorNoEncontradoException {
        if (marketplace.getListaProductos().remove(producto)) {
            vendedor.getListaProductos().remove(producto);
            logger.logInfo("El producto " + producto.getNombre() + " ha sido removido");
        } else {
            logger.logInfo("El producto no existe");
            throw new VendedorNoEncontradoException("El producto no existe");
        }
    }

    public void removerVendedor(Marketplace marketplace, Vendedor vendedor) {
        marketplace.getListaVendedores().remove(vendedor);
        logger.logInfo("El vendedor " + vendedor.getNombre() + " ha sido removido");
    }

    public String mostrarVendedor(Vendedor vendedor) {
        return vendedor.toString();
    }

    public String mostrarProducto(Producto producto) {
        return producto.toString();
    }

    public void editarVendedor(Marketplace marketplace, Vendedor vendedor, String newNombre, String newDescripcion, String newContrasenia, String newDireccion, String newCedula) throws UsuarioYaExisteException {
        // Verificar si otro vendedor ya tiene el nuevo nombre o cédula
        for (Vendedor v : marketplace.getListaVendedores()) {
            // Verificamos si hay un vendedor diferente que tiene el mismo nombre o cédula
            if (!v.equals(vendedor) && (v.getNombre().equals(newNombre) || v.getCedula().equals(newCedula))) {
                throw new UsuarioYaExisteException("Ya existe un vendedor con el mismo nombre o cédula.");
            }
        }

        // Si no se lanza la excepción, procede a editar el vendedor
        vendedor.setNombre(newNombre);
        vendedor.setDescripcion(newDescripcion);
        vendedor.setDireccion(newDireccion);
        vendedor.setContrasenia(newContrasenia);
        vendedor.setCedula(newCedula);
        MarketplaceManager.setMarketplaceInstance(marketplace);

        logger.logInfo("Vendedor " + vendedor.getNombre() + " editado satisfactoriamente");
    }

    public Producto editarProducto(Marketplace marketplace, Vendedor vendedor, Producto producto, String newNombre, String newCodigo, String newImagen, String newPrecio, LocalDate newFechaPublicacion, Categoria newCategoria, int newLikes, Estado newEstado) throws ProductoYaExisteException {
        for (Producto v : marketplace.getListaProductos()) {
            if (Objects.equals(v.getCodigo(), newCodigo) && !v.equals(producto)) {
                logger.logWarning("Se intentó editar el producto " + producto.getNombre() + ", pero ya existe otro producto con el código ingresado");
                throw new ProductoYaExisteException("Ya existe un producto con este código");
            }
        }

        // Si no se lanza la excepción, procede a editar el producto
        producto.setNombre(newNombre);
        producto.setCodigo(newCodigo);
        producto.setImagen(newImagen);
        producto.setPrecio(newPrecio);
        producto.setFechaPublicacion(newFechaPublicacion);
        producto.setCategoria(newCategoria);
        producto.setLikes(newLikes);
        producto.setEstado(newEstado);
        logger.logInfo("Producto " + producto.getNombre() + " editado satisfactoriamente");

        return producto;
    }
}
