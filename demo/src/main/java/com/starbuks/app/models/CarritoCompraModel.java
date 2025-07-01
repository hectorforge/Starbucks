package com.starbuks.app.models;

import com.starbuks.app.entitys.bean.CarritoCompra;
import com.starbuks.app.entitys.bean.DetalleVenta;
import com.starbuks.app.entitys.bean.Venta;
import com.starbuks.app.exception.StockInsuficienteException;
import com.starbuks.app.entitys.bean.ItemCarrito;
import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.persistence.CarritoCompraRepository;
import com.starbuks.app.persistence.ItemCarritoRepository;
import com.starbuks.app.persistence.ProductoRepository;
import com.starbuks.app.persistence.UsuarioRepository;
import com.starbuks.app.persistence.VentaRepository;
import com.starbuks.app.usecase.CarritoCompraUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CarritoCompraModel implements CarritoCompraUseCase {

    private final CarritoCompraRepository carritoRepo;
    private final ItemCarritoRepository itemRepo;
    private final ProductoRepository productoRepo;
    private final UsuarioRepository usuarioRepo;
    private final VentaRepository ventaRepository;

    @Override
    public void agregarAlCarrito(Long usuarioId, Long productoId, int cantidad) {
        CarritoCompra carrito = carritoRepo.findByUsuarioIdAndPagadoFalse(usuarioId)
            .stream().findFirst().orElseGet(() -> {
                CarritoCompra nuevo = new CarritoCompra();
                Usuario usuario = usuarioRepo.findById(usuarioId)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                nuevo.setUsuario(usuario);
                nuevo.setItems(new ArrayList<>());
                nuevo.setPagado(false);
                return carritoRepo.save(nuevo);
            });

        Producto producto = productoRepo.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

       
        ItemCarrito itemExistente = carrito.getItems().stream()
            .filter(i -> i.getProducto().getId().equals(productoId))
            .findFirst()
            .orElse(null);

        if (itemExistente != null) {
          
            itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
            itemRepo.save(itemExistente);
        } else {
        
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(cantidad);
            nuevoItem.setCarrito(carrito);
            itemRepo.save(nuevoItem);
        }
    }

    @Override
    public CarritoCompra obtenerCarrito(Long usuarioId) {
        return carritoRepo.findByUsuarioIdAndPagadoFalse(usuarioId)
                .stream().findFirst().orElse(null);
    }

    @Override
    @Transactional
    public void pagarCarrito(Long usuarioId) {
        CarritoCompra carrito = obtenerCarrito(usuarioId);
        if (carrito == null || carrito.getItems().isEmpty()) {
            throw new RuntimeException("No hay carrito pendiente de pago");
        }

        Usuario usuario = carrito.getUsuario();

        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = item.getProducto();
            int nuevoStock = producto.getStock() - item.getCantidad();
            if (nuevoStock < 0) {
            	throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre());

            }

            // Actualiza stock
            producto.setStock(nuevoStock);
            productoRepo.save(producto);

            // Crear una venta individual por producto
            Venta venta = new Venta();
            venta.setUsuario(usuario);
            venta.setFecha(LocalDateTime.now());
            venta.setModalidad("Online");
            venta.setTotal(BigDecimal.ZERO);
            venta.setDetalles(new ArrayList<>());

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubTotal(producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
            detalle.setVenta(venta);

            venta.getDetalles().add(detalle);
            venta.setTotal(detalle.getSubTotal());

            ventaRepository.save(venta);
        }

        carrito.setPagado(true);
        carrito.getItems().clear();
        carritoRepo.save(carrito);
    }
    @Override
    @Transactional
    public void eliminarDelCarrito(Long usuarioId, Long productoId) {
        CarritoCompra carrito = obtenerCarrito(usuarioId);
        if (carrito == null) return;

        ItemCarrito item = carrito.getItems().stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(null);

        if (item != null) {
            carrito.getItems().remove(item); 
            itemRepo.delete(item);
        }
    }
    
    @Override
    @Transactional
    public void disminuirCantidad(Long usuarioId, Long productoId) {
        CarritoCompra carrito = obtenerCarrito(usuarioId);
        if (carrito == null) return;

        ItemCarrito item = carrito.getItems().stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(null);

        if (item != null) {
            if (item.getCantidad() > 1) {
                item.setCantidad(item.getCantidad() - 1);
                itemRepo.save(item);
            } else {
                itemRepo.delete(item);
            }
        }
    }
    
}