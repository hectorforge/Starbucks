package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.*;
import com.starbuks.app.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // GET /carrito/{usuarioId}
    @GetMapping("/{usuarioId}")
    public ResponseEntity<?> obtenerCarritoPorUsuario(@PathVariable Long usuarioId) {
        var carritoOpt = carritoRepository.findByUsuarioId(usuarioId);
        if (carritoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Carrito carrito = carritoOpt.get();
        List<CarritoItem> items = carritoItemRepository.findByCarritoId(carrito.getId());
        return ResponseEntity.ok(items);
    }

    // POST /carrito/agregar
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarProductoAlCarrito(@RequestBody AgregarCarritoRequest request) {
        var usuarioOpt = usuarioRepository.findById(request.getUsuarioId());
        var productoOpt = productoRepository.findById(request.getProductoId());

        if (usuarioOpt.isEmpty() || productoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario o Producto no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        Producto producto = productoOpt.get();

        Carrito carrito = carritoRepository.findByUsuarioId(usuario.getId())
                .orElseGet(() -> {
                    Carrito c = new Carrito();
                    c.setUsuario(usuario);
                    c.setFechaCreacion(LocalDateTime.now());
                    return carritoRepository.save(c);
                });

        // Buscar si el producto ya está en el carrito
        List<CarritoItem> items = carritoItemRepository.findByCarritoId(carrito.getId());
        CarritoItem carritoItem = items.stream()
                .filter(i -> i.getProducto().getId().equals(producto.getId()))
                .findFirst()
                .orElse(null);

        if (carritoItem == null) {
            carritoItem = new CarritoItem();
            carritoItem.setCarrito(carrito);
            carritoItem.setProducto(producto);
            carritoItem.setCantidad(request.getCantidad());
        } else {
            carritoItem.setCantidad(carritoItem.getCantidad() + request.getCantidad());
        }
        carritoItemRepository.save(carritoItem);

        return ResponseEntity.ok(carritoItem);
    }

    // DELETE /carrito/item/{id}
    @DeleteMapping("/item/{id}")
    public ResponseEntity<?> eliminarItemCarrito(@PathVariable Long id) {
        if (!carritoItemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        carritoItemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // DELETE /carrito/usuario/{id} (vaciar carrito)
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> vaciarCarritoPorUsuario(@PathVariable Long usuarioId) {
        var carritoOpt = carritoRepository.findByUsuarioId(usuarioId);
        if (carritoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Carrito carrito = carritoOpt.get();
        carritoItemRepository.deleteByCarritoId(carrito.getId());
        return ResponseEntity.ok().build();
    }

    // DTO para agregar producto al carrito
    public static class AgregarCarritoRequest {
        private Long usuarioId;
        private Long productoId;
        private int cantidad;

        public Long getUsuarioId() {
            return usuarioId;
        }
        public void setUsuarioId(Long usuarioId) {
            this.usuarioId = usuarioId;
        }
        public Long getProductoId() {
            return productoId;
        }
        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }
        public int getCantidad() {
            return cantidad;
        }
        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }

}
