package com.startbucks.usecase;

import com.startbucks.entitys.bean.Categoria;
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
