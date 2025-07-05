package com.starbuks.app.controller;

import com.starbuks.app.dtos.CarritoCompraDTO;
import com.starbuks.app.dtos.ItemCarritoDTO;
import com.starbuks.app.entitys.bean.CarritoCompra;
import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.exception.StockInsuficienteException;
import com.starbuks.app.usecase.CarritoCompraUseCase;
import com.starbuks.app.usecase.ProductoUseCase;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;

//import java.util.List;

//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrito")
@RequiredArgsConstructor
public class CarritoCompraController {

    private final CarritoCompraUseCase carritoUseCase;
    
    private final ProductoUseCase productoUseCase;

    @PostMapping("/agregar")
    @ResponseBody
    public ResponseEntity<?> agregarProducto(@RequestParam Long usuarioId,
                                             @RequestParam Long productoId,
                                             @RequestParam int cantidad) {
        Producto producto = productoUseCase.obtenerPorId(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < cantidad) {
            return ResponseEntity
                .badRequest()
                .body("Stock insuficiente para el producto: " + producto.getNombre());
        }

        carritoUseCase.agregarAlCarrito(usuarioId, productoId, cantidad);
        return ResponseEntity.ok("Producto agregado al carrito");
    }


    @GetMapping("/{usuarioId}")
    public String verCarrito(@PathVariable Long usuarioId, Model model) {
        CarritoCompra carrito = carritoUseCase.obtenerCarrito(usuarioId);
        CarritoCompraDTO dto = new CarritoCompraDTO();
        int carritoCantidad = 0;

        if (carrito != null) {
            dto.setUsuarioId(carrito.getUsuario().getId());

            var items = carrito.getItems().stream().map(item -> {
                ItemCarritoDTO itemDTO = new ItemCarritoDTO();
                itemDTO.setProductoId(item.getProducto().getId());
                itemDTO.setCantidad(item.getCantidad());
                itemDTO.setUsuarioId(carrito.getUsuario().getId());
                itemDTO.setNombreProducto(item.getProducto().getNombre());
                itemDTO.setPrecioUnitario(item.getProducto().getPrecio());
                return itemDTO;
            }).toList();

            dto.setItems(items);

            BigDecimal total = items.stream()
                .map(i -> i.getPrecioUnitario().multiply(BigDecimal.valueOf(i.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setTotal(total);

            
            carritoCantidad = items.stream()
                .mapToInt(ItemCarritoDTO::getCantidad)
                .sum();
        }

        model.addAttribute("carrito", dto);
        model.addAttribute("usuarioId", usuarioId);
        model.addAttribute("carritoCantidad", carritoCantidad);

        return "cliente/carrito";
    }


    @PostMapping("/pagar")
    public String pagar(@RequestParam Long usuarioId,
                        RedirectAttributes redirectAttributes) {
        try {
            carritoUseCase.pagarCarrito(usuarioId);
            return "cliente/pago-exitoso";
        } catch (StockInsuficienteException e) {
            // 1️⃣ guardamos el mensaje de error en flash
            redirectAttributes.addFlashAttribute("errorStock", e.getMessage());
            // 2️⃣ redirigimos de vuelta al carrito
            return "redirect:/carrito/" + usuarioId;
        }
    }
    
    @PostMapping("/eliminar")
    public String eliminarItemDelCarrito(@RequestParam Long usuarioId,
                                         @RequestParam Long productoId,
                                         RedirectAttributes redirectAttributes) {
        carritoUseCase.eliminarDelCarrito(usuarioId, productoId);
        redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado del carrito.");
        return "redirect:/carrito/" + usuarioId;
    }
    
    @PostMapping("/disminuir")
    public String disminuirCantidad(@RequestParam Long usuarioId,
                                    @RequestParam Long productoId) {
        carritoUseCase.disminuirCantidad(usuarioId, productoId);
        return "redirect:/carrito/" + usuarioId;
    }
    
    
}
