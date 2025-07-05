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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaModel implements VentaUseCase {

    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public Page<Venta> listarVentasPorUsuario(Long usuarioId, Pageable pageable) {
        return ventaRepository.findByUsuarioId(usuarioId, pageable);
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
        // 1. Traer usuario
        Usuario usuario = usuarioRepository.findById(model.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Crear la venta
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setModalidad(model.getModalidad());
        venta.setUsuario(usuario);

        BigDecimal totalVenta = BigDecimal.ZERO;

        // 3. Recorrer todos los detalles y agregarlos a 'venta'
        for (DetalleVentaDTO dto : model.getDetalles()) {
            // validaciones...
            Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + dto.getProductoId()));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());
            detalle.setSubTotal(dto.getPrecioUnitario()
                                 .multiply(BigDecimal.valueOf(dto.getCantidad())));
            detalle.setVenta(venta);

            venta.getDetalles().add(detalle);
            totalVenta = totalVenta.add(detalle.getSubTotal());
        }

        // 4. Asignar el total acumulado y guardar solo una vez
        venta.setTotal(totalVenta);
        ventaRepository.save(venta);
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
    
    @Override
    public Optional<Venta> obtenerPorId(Long id) {
        return ventaRepository.findByIdConDetalles(id);
    }
    

}
