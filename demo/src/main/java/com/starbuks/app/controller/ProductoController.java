package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.usecase.ProductoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoUseCase productoServicio;

    // CRUD
    @GetMapping
    public List<Producto> listar() {
        return productoServicio.listar();
    }

    @GetMapping("/{id}")
    public Producto obtenerPorId(@PathVariable Long id) {
        return productoServicio.obtenerPorId(id);
    }

    @PostMapping
    public Producto registrar(@RequestBody Producto producto) {
        return productoServicio.registrar(producto);
    }

    @PutMapping
    public Producto actualizar(@RequestBody Producto producto) {
        return productoServicio.actualizar(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoServicio.eliminar(id);
    }

    // ADDS
    @GetMapping("/precio")
    public List<Producto> buscarPorRangoPrecio(
            @RequestParam Double min,
            @RequestParam Double max) {
        return productoServicio.buscarPorRangoPrecio(min, max);
    }

    @GetMapping("/unidad-medida")
    public List<Producto> buscarPorUnidadMedida(@RequestParam String unidad) {
        return productoServicio.buscarPorUnidadMedida(unidad);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Producto> listarPorCategoria(@PathVariable Long categoriaId) {
        return productoServicio.listarPorCategoria(categoriaId);
    }
}