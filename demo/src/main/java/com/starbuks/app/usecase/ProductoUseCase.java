package com.starbuks.app.usecase;

import java.util.List;

import com.starbuks.app.entitys.bean.Categoria;
import com.starbuks.app.entitys.bean.Producto;

public interface ProductoUseCase {
    
    // CRUD
    List<Producto> listar();
    Producto obtenerPorId(Long id);
    Producto registrar(Producto producto);
    Producto actualizar(Producto producto);
    void eliminar(Long id);

    // ADDS
    List<Producto> buscarPorRangoPrecio(Double precioMin, Double precioMax);
    List<Producto> buscarPorUnidadMedida(String unidadMedida);
    List<Producto> listarPorCategoria(Long categoriaId);
}