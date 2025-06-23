package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.CarritoCompra;
import com.starbuks.app.entitys.bean.ItemCarrito;
import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.usecase.CarritoCompraUseCase;
import com.starbuks.app.usecase.ProductoUseCase;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ProductoUseCase productoUseCase;
    private final CarritoCompraUseCase carritoUseCase; // ✅ Inyección agregada

    @GetMapping("/tienda")
    public String verTienda(Model model) {
        List<Producto> productos = productoUseCase.listarActivos();
        model.addAttribute("productos", productos);

        Long usuarioId = 1L; // Simulado hasta tener login
        CarritoCompra carrito = carritoUseCase.obtenerCarrito(usuarioId);
        int totalItems = carrito != null
                ? carrito.getItems().stream().mapToInt(ItemCarrito::getCantidad).sum()
                : 0;
        model.addAttribute("carritoCantidad", totalItems);
        model.addAttribute("usuarioId", usuarioId);

        return "cliente/tienda";
    }
}