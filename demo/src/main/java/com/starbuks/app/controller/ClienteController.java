package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.CarritoCompra;
import com.starbuks.app.entitys.bean.ItemCarrito;
import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.usecase.CarritoCompraUseCase;
import com.starbuks.app.usecase.CategoriaUseCase;
import com.starbuks.app.usecase.CompraUseCase;
import com.starbuks.app.usecase.ProductoUseCase;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ProductoUseCase productoUseCase;
    private final CarritoCompraUseCase carritoUseCase;
    private final CategoriaUseCase categoriaUseCase; 
    private final CompraUseCase compraUseCase;

    @GetMapping("/productos")
    public String verProductos(
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max,
            Model model) {

    	
        var categorias = categoriaUseCase.listarActivas();
        model.addAttribute("categorias", categorias);


        List<Producto> productos;
        if (categoria != null) {
            productos = productoUseCase.findByCategoriaActiva(categoria);
        } else if (min != null && max != null) {
            productos = productoUseCase.findByPrecioBetween(min, max);
        } else {
            productos = productoUseCase.findByActivoTrue();
        }
        model.addAttribute("productos", productos);

        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("categoria", categoria);


        Long usuarioId = 1L;
        model.addAttribute("usuarioId", usuarioId);
        var carrito = carritoUseCase.obtenerCarrito(usuarioId);
        int totalItems = carrito != null
                ? carrito.getItems().stream().mapToInt(ItemCarrito::getCantidad).sum()
                : 0;
        model.addAttribute("carritoCantidad", totalItems);

        return "cliente/productos";
    }
    
    @GetMapping("/inicio")
    public String inicio(Model model) {
        Long usuarioId = 1L;
        model.addAttribute("usuarioId", usuarioId);

        var carrito = carritoUseCase.obtenerCarrito(usuarioId);
        int totalItems = carrito != null
            ? carrito.getItems().stream().mapToInt(ItemCarrito::getCantidad).sum()
            : 0;
        model.addAttribute("carritoCantidad", totalItems);

        return "cliente/index";
    }
    
    @GetMapping("/miscompras")
    public String verMisCompras(Model model) {
        Long usuarioId = 1L; // Reemplazar por el usuario autenticado en un caso real
        var compras = compraUseCase.obtenerComprasPorUsuario(usuarioId);
        model.addAttribute("compras", compras);
        model.addAttribute("usuarioId", usuarioId);

        var carrito = carritoUseCase.obtenerCarrito(usuarioId);
        int totalItems = carrito != null
                ? carrito.getItems().stream().mapToInt(ItemCarrito::getCantidad).sum()
                : 0;
        model.addAttribute("carritoCantidad", totalItems);

        return "cliente/miscompras";
    }
}