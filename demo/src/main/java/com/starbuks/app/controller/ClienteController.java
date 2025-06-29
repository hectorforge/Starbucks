package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.ItemCarrito;
import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.security.CustomUserDetails;
import com.starbuks.app.usecase.CarritoCompraUseCase;
import com.starbuks.app.usecase.CategoriaUseCase;
import com.starbuks.app.usecase.ProductoUseCase;
import com.starbuks.app.usecase.VentaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ProductoUseCase productoUseCase;
    private final CarritoCompraUseCase carritoUseCase;
    private final CategoriaUseCase categoriaUseCase;
    private final VentaUseCase ventaUseCase;

    @GetMapping("/productos")
    public String verProductos(
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max,
            Model model,
            Authentication auth) {

        var categorias = categoriaUseCase.listarActivas();
        model.addAttribute("categorias", categorias);

        List<Producto> productos;
        if (categoria != null) {
            productos = productoUseCase.obtenerPorCategoriaActiva(categoria);
        } else if (min != null && max != null) {
            productos = productoUseCase.obtenerPorPrecioEntre(min, max);
        } else {
            productos = productoUseCase.obtenerPorActivoTrue();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("categoria", categoria);

        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            Usuario usuario = userDetails.getUsuario();
            Long usuarioId = usuario.getId();

            var carrito = carritoUseCase.obtenerCarrito(usuarioId);
            int totalItems = carrito != null
                    ? carrito.getItems().stream().mapToInt(ItemCarrito::getCantidad).sum()
                    : 0;
            model.addAttribute("carritoCantidad", totalItems);
            model.addAttribute("usuarioId", usuarioId);
        } else {
            model.addAttribute("carritoCantidad", 0);
            model.addAttribute("usuarioId", null);
        }

        return "cliente/productos";
    }

    @GetMapping("/inicio")
    public String inicio(Model model, Authentication auth) {
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            Usuario usuario = userDetails.getUsuario();
            Long usuarioId = usuario.getId();

            var carrito = carritoUseCase.obtenerCarrito(usuarioId);
            int totalItems = carrito != null
                    ? carrito.getItems().stream().mapToInt(ItemCarrito::getCantidad).sum()
                    : 0;
            model.addAttribute("carritoCantidad", totalItems);
            model.addAttribute("usuarioId", usuarioId);
        } else {
            model.addAttribute("carritoCantidad", 0);
            model.addAttribute("usuarioId", null);
        }

        return "cliente/index";
    }

    @GetMapping("/miscompras")
    public String verMisCompras(Model model, Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return "redirect:/login?redirect=miscompras";
        }

        Usuario usuario = userDetails.getUsuario();
        Long usuarioId = usuario.getId();

        var ventas = ventaUseCase.obtenerVentaPorUsuario(usuarioId);
        if (ventas == null) ventas = new ArrayList<>();
        ventas.removeIf(v -> v == null || v.getDetalles() == null);

        model.addAttribute("compras", ventas);
        model.addAttribute("usuarioId", usuarioId);

        var carrito = carritoUseCase.obtenerCarrito(usuarioId);
        int totalItems = carrito != null
                ? carrito.getItems().stream().mapToInt(ItemCarrito::getCantidad).sum()
                : 0;
        model.addAttribute("carritoCantidad", totalItems);

        return "cliente/miscompras";
    }
}