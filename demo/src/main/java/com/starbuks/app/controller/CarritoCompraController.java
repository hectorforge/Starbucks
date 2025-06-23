package com.starbuks.app.controller;

import com.starbuks.app.dtos.CarritoCompraDTO;
import com.starbuks.app.dtos.ItemCarritoDTO;
import com.starbuks.app.entitys.bean.CarritoCompra;
import com.starbuks.app.usecase.CarritoCompraUseCase;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

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

    @PostMapping("/agregar")
    public String agregarProducto(@RequestParam Long usuarioId,
                                  @RequestParam Long productoId,
                                  @RequestParam int cantidad) {
        carritoUseCase.agregarAlCarrito(usuarioId, productoId, cantidad);
        return "redirect:/cliente/tienda";
    }

    @GetMapping("/{usuarioId}")
    public String verCarrito(@PathVariable Long usuarioId, Model model) {
        CarritoCompra carrito = carritoUseCase.obtenerCarrito(usuarioId);
        CarritoCompraDTO dto = new CarritoCompraDTO();

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
        }
        model.addAttribute("carrito", dto);
        return "cliente/carrito";
    }

    @PostMapping("/pagar")
    public String pagar(@RequestParam Long usuarioId) {
        carritoUseCase.pagarCarrito(usuarioId);
        return "cliente/pago-exitoso";
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
