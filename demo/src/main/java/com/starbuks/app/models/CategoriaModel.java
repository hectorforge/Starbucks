package com.starbuks.app.models;

import com.starbuks.app.entitys.bean.Categoria;
import com.starbuks.app.persistence.CategoriaRepository;
import com.starbuks.app.persistence.ProductoRepository;
import com.starbuks.app.usecase.CategoriaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaModel implements CategoriaUseCase {

	 private final CategoriaRepository _categoriaRepository;
	    private final ProductoRepository _productoRepository;

	    @Override
	    public List<Categoria> listarCategorias() {
	        return _categoriaRepository.findAll();
	    }

	    @Override
	    public Categoria obtenerPorId(Long id) {
	        return _categoriaRepository.findById(id).orElse(null);
	    }

	    @Override
	    public Categoria registrarCategoria(Categoria categoria) {
	        return _categoriaRepository.save(categoria);
	    }

	    @Override
	    public Categoria actualizarCategoria(Categoria categoria) {
	        return _categoriaRepository.save(categoria);
	    }

	    @Override
	    public void eliminarPorId(Long id) {
	        _categoriaRepository.deleteById(id);
	    }

	    @Override
	    public List<Categoria> listarActivas() {
	        return _categoriaRepository.findByActivoTrue();
	    }

	    @Override
	    public boolean existeCategoriaConNombre(String nombre) {
	        return _categoriaRepository.existsByNombreIgnoreCase(nombre);
	    }

	    @Override
	    public long contarProductosPorCategoria(Long categoriaId) {
	        return _productoRepository.countByCategoriaId_Id(categoriaId);
	    }
}
