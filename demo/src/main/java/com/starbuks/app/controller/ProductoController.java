package com.starbuks.app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.usecase.ProductoUseCase;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoUseCase productoUseCase;


    @GetMapping
    public List<Producto> listarTodos() {
        return productoUseCase.findAll();
    }


    @GetMapping("/activos")
    public List<Producto> listarActivos() {
        return productoUseCase.findByActivoTrue();
    }


    @GetMapping("/{id}")
    public Optional<Producto> obtenerPorIdActivo(@PathVariable Long id) {
        return productoUseCase.findByIdAndActivoTrue(id);
    }


    @GetMapping("/buscar/nombre")
    public List<Producto> buscarPorNombre(@RequestParam String nombre) {
        return productoUseCase.findByNombreContainingIgnoreCase(nombre);
    }


    @GetMapping("/buscar/precio")
    public List<Producto> buscarPorPrecio(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max
    ) {
        return productoUseCase.findByPrecioBetween(min, max);
    }
 //
    @GetMapping("/buscar/stock")
    public List<Producto> buscarPorStockMinimo(@RequestParam int cantidad) {
        return productoUseCase.findByStockGreaterThanEqual(cantidad);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoUseCase.deleteById(id);
    }
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoUseCase.save(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(
        @PathVariable Long id,
        @RequestBody Producto producto
    ) {
        return productoUseCase.update(id, producto);
    }
}