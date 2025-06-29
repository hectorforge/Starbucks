package com.starbuks.app.models;


import com.starbuks.app.dtos.DetalleVentaDTO;
import com.starbuks.app.dtos.VentaModelDTO;
import com.starbuks.app.entitys.bean.DetalleVenta;
import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.entitys.bean.Venta;
import com.starbuks.app.persistence.ProductoRepository;
import com.starbuks.app.persistence.UsuarioRepository;
import com.starbuks.app.persistence.VentaRepository;
import com.starbuks.app.usecase.VentaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaModel implements VentaUseCase {

    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<Venta> obtenerVentaPorUsuario(Long usuarioId) {
        return ventaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAllConDetalles();
    }

    public List<Venta> buscarPorCliente(String termino) {
        return ventaRepository.buscarPorCliente(termino);
    }

    @Override
    public void registrarVenta(VentaModelDTO model) {
        Usuario usuario = usuarioRepository.findById(model.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        for (DetalleVentaDTO dto : model.getDetalles()) {
            Long productoId = dto.getProductoId();
            Integer cantidad = dto.getCantidad();
            BigDecimal precioUnitario = dto.getPrecioUnitario();

            if (productoId == null || cantidad == null || cantidad <= 0 || 
                precioUnitario == null || precioUnitario.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("❌ Detalle inválido. Saltando...");
                continue;
            }

            Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: ID " + productoId));

            // Crear nueva venta por cada producto
            Venta venta = new Venta();
            venta.setFecha(LocalDateTime.now());
            venta.setModalidad(model.getModalidad());
            venta.setUsuario(usuario);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubTotal(precioUnitario.multiply(BigDecimal.valueOf(cantidad)));
            detalle.setVenta(venta); // relación bidireccional

            venta.getDetalles().add(detalle);
            venta.setTotal(detalle.getSubTotal());

            System.out.println("✅ Registrando venta individual: " + producto.getNombre());
            ventaRepository.save(venta); // Guarda una venta con un solo detalle
        }
    }

    public List<Venta> buscarPorProducto(String termino) {
        return ventaRepository.buscarPorProducto(termino);
    }

    @Override
    public List<Venta> buscarPorFecha(LocalDate fecha) {
        return ventaRepository.buscarPorFecha(fecha);
    }

    @Override
    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }

}
