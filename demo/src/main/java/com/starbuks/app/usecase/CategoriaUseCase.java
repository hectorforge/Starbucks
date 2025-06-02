package com.starbuks.app.usecase;

import com.starbuks.app.entitys.bean.Categoria;
import java.util.List;

public interface CategoriaUseCase {

    // CRUD
    List<Categoria> listar();
    Categoria obtenerPorId(Long id);
    Categoria registrar(Categoria categoria);
    Categoria actualizar(Categoria categoria);
    void eliminar(Long id);

    // ADDS
    List<Categoria> listarActivas();
    boolean existeCategoriaConNombre(String nombre);
    long contarProductosPorCategoria(Long categoriaId);
}
