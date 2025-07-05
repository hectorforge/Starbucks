package com.starbuks.app.controller;


import com.starbuks.app.dtos.DetalleVentaDTO;
import com.starbuks.app.dtos.VentaModelDTO;
import com.starbuks.app.entitys.bean.Venta;
import com.starbuks.app.exception.StockInsuficienteException;
import com.starbuks.app.usecase.ProductoUseCase;
import com.starbuks.app.usecase.UsuarioUseCase;
import com.starbuks.app.usecase.VentaUseCase;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaUseCase ventaUseCase;
    private final ProductoUseCase productoUseCase;
    private final UsuarioUseCase usuarioUseCase;

    // Mostrar lista de ventas
    @GetMapping
    public String listarVentas(Model model) {
        List<Venta> ventas = ventaUseCase.listarVentas();
        model.addAttribute("ventas", ventas != null ? ventas : new ArrayList<>());
        return "ventas/listar";
    }

    // Mostrar formulario de registro de venta
    @GetMapping("/registrar")
    public String mostrarFormulario(Model model) {
        VentaModelDTO ventaDTO = new VentaModelDTO();
        ventaDTO.getDetalles().add(new DetalleVentaDTO()); // Agregamos una línea por defecto

        model.addAttribute("venta", ventaDTO);
        model.addAttribute("productos", productoUseCase.listarProductos());
        model.addAttribute("usuarios", usuarioUseCase.listarUsuarios());

        return "ventas/registrar";
    }

    // Guardar venta
    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute("venta") VentaModelDTO ventaModel,
                               RedirectAttributes attrs) {
        if (ventaModel.getDetalles() == null || ventaModel.getDetalles().isEmpty()) {
            return "redirect:/ventas/registrar?error=debesAgregarProductos";
        }

        try {
            ventaUseCase.registrarVenta(ventaModel);
            return "redirect:/ventas";
        } catch (StockInsuficienteException ex) {
            // reenviamos al form con el mensaje en un query‐param
            attrs.addAttribute("errorStock", ex.getMessage());
            return "redirect:/ventas/registrar";
        }
    }

    // Buscar venta por cliente, producto o fecha
    @GetMapping("/buscar")
    public String buscar(@RequestParam("tipo") String tipo,
                         @RequestParam("termino") String termino,
                         Model model) {

        List<Venta> ventas;

        switch (tipo.toLowerCase()) {
            case "cliente":
                ventas = ventaUseCase.buscarPorCliente(termino);
                break;
            case "producto":
                ventas = ventaUseCase.buscarPorProducto(termino);
                break;
            case "fecha":
                LocalDate fecha = LocalDate.parse(termino, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                ventas = ventaUseCase.buscarPorFecha(fecha);
                break;
            default:
                ventas = ventaUseCase.listarVentas();
        }

        model.addAttribute("ventas", ventas);
        return "ventas/listar";
    }

    // Eliminar venta
    @PostMapping("/eliminar")
    public String eliminarVenta(@RequestParam("id") Long id) {
        ventaUseCase.eliminarVenta(id);
        return "redirect:/ventas";
    }
}