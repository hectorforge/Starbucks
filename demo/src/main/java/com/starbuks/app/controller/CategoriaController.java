package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.Categoria;
import com.starbuks.app.usecase.CategoriaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@RequiredArgsConstructor
public class CategoriaController {

	 private final CategoriaUseCase categoriaServicio;

	    // CRUD
	    @GetMapping
	    public List<Categoria> listar() {
	        return categoriaServicio.listar();
	    }

	    @GetMapping("/{id}")
	    public Categoria obtenerPorId(@PathVariable Long id) {
	        return categoriaServicio.obtenerPorId(id);
	    }

	    @PostMapping
	    public Categoria registrar(@RequestBody Categoria categoria) {
	        return categoriaServicio.registrar(categoria);
	    }

	    @PutMapping
	    public Categoria actualizar(@RequestBody Categoria categoria) {
	        return categoriaServicio.actualizar(categoria);
	    }

	    @DeleteMapping("/{id}")
	    public void eliminar(@PathVariable Long id) {
	        categoriaServicio.eliminar(id);
	    }

	    // ADDS
	    @GetMapping("/activas")
	    public List<Categoria> listarActivas() {
	        return categoriaServicio.listarActivas();
	    }

	    @GetMapping("/existe")
	    public boolean existeCategoria(@RequestParam String nombre) {
	        return categoriaServicio.existeCategoriaConNombre(nombre);
	    }

	    @GetMapping("/{id}/productos/count")
	    public long contarProductosPorCategoria(@PathVariable Long id) {
	        return categoriaServicio.contarProductosPorCategoria(id);
	    }
}
