package com.starbuks.app.usecase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.starbuks.app.entitys.bean.Producto;

public interface ProductoUseCase {

	public List<Producto> listarProductos();
    public List<Producto> obtenerPorActivoTrue();
    public List<Producto> obtenerPorPrecioEntre(BigDecimal min, BigDecimal max);
    public Optional<Producto> obtenerPorId(Long id);
    public Producto registrarProducto(Producto producto);
    public Producto actualizarProducto(Long id, Producto producto);
    public void eliminarPorId(Long id);
    public List<Producto> obtenerPorCategoriaActiva(Long categoriaId);
    
    
}
