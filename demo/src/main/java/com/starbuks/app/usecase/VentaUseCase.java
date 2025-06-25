package com.starbuks.app.usecase;

import com.starbuks.app.dtos.VentaModelDTO;
import com.starbuks.app.entitys.bean.Venta;

import java.time.LocalDate;
import java.util.List;

public interface VentaUseCase {
    public List<Venta> obtenerVentaPorUsuario(Long usuarioId);
    public List<Venta> obtenerTodas();
    public List<Venta> buscarPorCliente(String termino);
    public void registrarVenta(VentaModelDTO ventaModel);
    public List<Venta> buscarPorProducto(String termino);
    public List<Venta> buscarPorFecha(LocalDate fecha);
    public void eliminarVenta(Long id);
}