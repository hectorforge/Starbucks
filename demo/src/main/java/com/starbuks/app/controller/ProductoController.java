package com.starbuks.app.controller;

import com.starbuks.app.dtos.ProductoDTO;
import com.starbuks.app.entitys.bean.Categoria;
import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.usecase.CategoriaUseCase;
import com.starbuks.app.usecase.ProductoUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
		List<Producto> productos = productoUseCase.listarProductos();
		model.addAttribute("productos", productos);
		return "producto/listar";
	}

	// FORMULARIO PARA CREAR
	@GetMapping("/nuevo")
	public String mostrarFormularioNuevo(Model model) {
		model.addAttribute("producto", new ProductoDTO());
		model.addAttribute("productoId", null);
		model.addAttribute("categorias", categoriaUseCase.listarCategorias());
		return "producto/formulario";
	}

	// GUARDAR NUEVO PRODUCTO
	@PostMapping("/guardar")
	public String guardarProducto(@ModelAttribute ProductoDTO dto) {
		Producto producto = convertirDtoAEntidad(dto);
		productoUseCase.registrarProducto(producto);
		return "redirect:/productos";
	}

	// FORMULARIO PARA EDITAR
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
		Producto producto = productoUseCase.obtenerPorId(id).orElseThrow();

		ProductoDTO dto = new ProductoDTO();
		dto.setNombre(producto.getNombre());
		dto.setDescripcion(producto.getDescripcion());
		dto.setPrecio(producto.getPrecio());
		dto.setStock(producto.getStock());
		dto.setActivo(producto.getActivo());
		dto.setCodigo(producto.getCodigo());
		dto.setImagenUrl(producto.getImagenUrl());
		dto.setUnidadMedida(producto.getUnidadMedida());
		dto.setPeso(producto.getPeso());

		if (producto.getCategoriaId() != null)
			dto.setCategoriaId(producto.getCategoriaId().getId());

		model.addAttribute("productoId", id);
		model.addAttribute("producto", dto);
		model.addAttribute("categorias", categoriaUseCase.listarCategorias());
		return "producto/formulario";
	}

	// ACTUALIZAR PRODUCTO
	@PostMapping("/actualizar/{id}")
	public String actualizarProducto(@PathVariable Long id, @ModelAttribute ProductoDTO dto) {
		Producto actualizado = convertirDtoAEntidad(dto, id);
		productoUseCase.actualizarProducto(id, actualizado);
		return "redirect:/productos";
	}

	// ELIMINAR PRODUCTO
	@GetMapping("/eliminar/{id}")
	public String eliminarProducto(@PathVariable Long id) {
		productoUseCase.eliminarPorId(id);
		return "redirect:/productos";
	}

	// Convertir DTO a Entidad (nuevo)
	private Producto convertirDtoAEntidad(ProductoDTO dto, Long id) {
		Producto p = convertirDtoAEntidad(dto);
		p.setId(id);
		return p;
	}

	// Convertir DTO a Entidad (base)
	private Producto convertirDtoAEntidad(ProductoDTO dto) {
		Producto p = new Producto();
		p.setNombre(dto.getNombre());
		p.setDescripcion(dto.getDescripcion());
		p.setPrecio(dto.getPrecio());
		p.setStock(dto.getStock());
		p.setActivo(dto.getActivo());
		p.setCodigo(dto.getCodigo());
		p.setImagenUrl(dto.getImagenUrl());
		p.setUnidadMedida(dto.getUnidadMedida());
		p.setPeso(dto.getPeso());

		if (dto.getCategoriaId() != null) {
			Categoria categoria = categoriaUseCase.obtenerPorId(dto.getCategoriaId());
			p.setCategoriaId(categoria);
		}
		return p;
	}
}
