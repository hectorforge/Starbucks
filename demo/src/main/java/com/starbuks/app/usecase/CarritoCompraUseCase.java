package com.starbuks.app.usecase;

import com.starbuks.app.entitys.bean.CarritoCompra;

public interface CarritoCompraUseCase {

    void agregarAlCarrito(Long usuarioId, Long productoId, int cantidad);

    CarritoCompra obtenerCarrito(Long usuarioId);

    void pagarCarrito(Long usuarioId);
    
    void eliminarDelCarrito(Long usuarioId, Long productoId);
    
    void disminuirCantidad(Long usuarioId, Long productoId);

}

