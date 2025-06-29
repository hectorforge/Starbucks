package com.starbuks.app.usecase;

import com.starbuks.app.entitys.bean.Categoria;
import java.util.List;

public interface CategoriaUseCase {

	 // CRUD
    List<Categoria> listarCategorias();
    Categoria obtenerPorId(Long id);
    Categoria registrarCategoria(Categoria categoria);
    Categoria actualizarCategoria(Categoria categoria);
    void eliminarPorId(Long id);

    // ADDS
    List<Categoria> listarActivas();
    boolean existeCategoriaConNombre(String nombre);
    long contarProductosPorCategoria(Long categoriaId);
}
