package com.starbuks.app.models;


import com.starbuks.app.dtos.VentaModelDTO;

import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.entitys.bean.Venta;
import com.starbuks.app.persistence.ProductoRepository;
import com.starbuks.app.persistence.UsuarioRepository;
import com.starbuks.app.persistence.VentaRepository;
import com.starbuks.app.usecase.VentaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public List<Venta> obtenerTodas() {
        return ventaRepository.findAll();
    }
    
    public List<Venta> buscarPorCliente(String termino) {
        return ventaRepository.buscarPorCliente(termino);
    }
    
    @Override
    public void registrarVenta(VentaModelDTO model) {
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setModalidad(model.getModalidad());

        Usuario usuario = usuarioRepository.findById(model.getUsuarioId()).orElseThrow();
        Producto producto = productoRepository.findById(model.getProductoId()).orElseThrow();

        venta.setUsuario(usuario);
        venta.setProducto(producto);
        venta.setCantidad(model.getCantidad());
        venta.setPrecioUnitario(model.getPrecioUnitario());

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
    
}