package com.starbuks.app.usecase;

import com.starbuks.app.dtos.VentaModelDTO;
import com.starbuks.app.entitys.bean.Venta;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface VentaUseCase {
    public List<Venta> listarVentas();
    public List<Venta> buscarPorCliente(String termino);
    public void registrarVenta(VentaModelDTO ventaModel);
    public List<Venta> buscarPorProducto(String termino);
    public List<Venta> buscarPorFecha(LocalDate fecha);
    public void eliminarVenta(Long id);
    Optional<Venta> obtenerPorId(Long id);
    Page<Venta> listarVentasPorUsuario(Long usuarioId, Pageable pageable);
}