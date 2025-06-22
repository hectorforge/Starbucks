package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.entitys.bean.Categoria;
import com.starbuks.app.usecase.CategoriaUseCase;
import com.starbuks.app.usecase.ProductoUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

	private final ProductoUseCase productoUseCase;
	private final CategoriaUseCase categoriaUseCase;

	// LISTAR TODOS LOS PRODUCTOS
	@GetMapping()
	public String listarProductos(Model model) {
		List<Producto> productos = productoUseCase.findAll();
		model.addAttribute("productos", productos);
		return "producto/listar";
	}

	// FORMULARIO PARA CREAR
	@GetMapping("/nuevo")
	public String mostrarFormularioNuevo(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("categorias", categoriaUseCase.listar());
		return "producto/formulario";
	}

	// GUARDAR NUEVO PRODUCTO
	@PostMapping("/guardar")
	public String guardarProducto(@ModelAttribute Producto producto) {
		productoUseCase.save(producto);
		return "redirect:/productos";
	}

	// EDITAR
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
		Producto producto = productoUseCase.findById(id).orElseThrow();
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", categoriaUseCase.listar());
		return "producto/formulario";
	}

	// ACTUALIZAR
	@PostMapping("/actualizar/{id}")
	public String actualizarProducto(@PathVariable Long id, @ModelAttribute Producto producto) {
		productoUseCase.update(id, producto);
		return "redirect:/productos";
	}

	// ELIMINAR
	@GetMapping("/eliminar/{id}")
	public String eliminarProducto(@PathVariable Long id) {
		productoUseCase.deleteById(id);
		return "redirect:/productos";
	}
}