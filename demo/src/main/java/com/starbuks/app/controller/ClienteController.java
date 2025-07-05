package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.ItemCarrito;
import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.entitys.bean.Venta;
import com.starbuks.app.security.CustomUserDetails;
import com.starbuks.app.service.BoletaService;
import com.starbuks.app.usecase.CarritoCompraUseCase;
import com.starbuks.app.usecase.CategoriaUseCase;
import com.starbuks.app.usecase.ProductoUseCase;
import com.starbuks.app.usecase.VentaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Date;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ProductoUseCase productoUseCase;
    private final CarritoCompraUseCase carritoUseCase;
    private final CategoriaUseCase categoriaUseCase;
    private final VentaUseCase ventaUseCase;
    private final BoletaService boletaService;

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
    public String verMisCompras(
            @RequestParam(defaultValue = "0") int page,    // 1️⃣ recibimos el número de página
            Model model,
            Authentication auth) {

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails ud)) {
            return "redirect:/login?redirect=miscompras";
        }

        Long usuarioId = ud.getUsuario().getId();

        // 2️⃣ Creamos el Pageable: página 'page', tamaño 5, ordenado por fecha DESC
        Pageable pageable = PageRequest.of(page, 5, Sort.by("fecha").descending());

        // 3️⃣ Llamamos al usecase paginado
        Page<Venta> comprasPage = ventaUseCase.listarVentasPorUsuario(usuarioId, pageable);

        model.addAttribute("compras", comprasPage);
        model.addAttribute("usuarioId", usuarioId);

        // 4️⃣ datos de carrito (igual que antes)
        var carrito = carritoUseCase.obtenerCarrito(usuarioId);
        int totalItems = carrito != null
                ? carrito.getItems().stream().mapToInt(ItemCarrito::getCantidad).sum()
                : 0;
        model.addAttribute("carritoCantidad", totalItems);

        return "cliente/miscompras";
    }
    
    @GetMapping("/boleta/{id}")
    public ResponseEntity<byte[]> descargarBoleta(@PathVariable Long id) {
        Venta venta = ventaUseCase.obtenerPorId(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Venta no encontrada"));

        try {
            // 1. Prepara los parámetros existentes
            String voucherId       = venta.getId().toString();
            String clientName      = venta.getUsuario().getNombres() 
                                     + " " + venta.getUsuario().getApellidos();
            String clientDocument  = venta.getUsuario().getEmail();
            Date   saleDate        = Date.from(
                                     venta.getFecha()
                                          .atZone(ZoneId.systemDefault())
                                          .toInstant());
            String paymentMethod   = venta.getModalidad();

            // 2. Genera el PDF
            byte[] pdf = boletaService.generarBoleta(
                venta.getId(),
                voucherId,
                clientName,
                clientDocument,
                saleDate,
                paymentMethod
            );

            // 3. Construye la respuesta HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                ContentDisposition.builder("inline")
                    .filename("boleta_" + voucherId + ".pdf")
                    .build()
            );
            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);

        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error generando boleta", e);
        }
    }
}