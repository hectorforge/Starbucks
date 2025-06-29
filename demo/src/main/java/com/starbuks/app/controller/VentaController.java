package com.starbuks.app.controller;


import com.starbuks.app.dtos.VentaModelDTO;
import com.starbuks.app.entitys.bean.Venta;
import com.starbuks.app.usecase.ProductoUseCase;
import com.starbuks.app.usecase.UsuarioUseCase;
import com.starbuks.app.usecase.VentaUseCase;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {
	
	private final VentaUseCase ventaUseCase;
	private final ProductoUseCase productoUseCase;
	private final UsuarioUseCase usuarioUseCase;
    // LISTAR TODOS LAS VENTAS
	 @GetMapping
	    public String listarVentas(Model model) {
	        model.addAttribute("ventas", ventaUseCase.listarVentas());
	        return "ventas/listar";
	    }
	 
	
	 
	 @GetMapping("/ventas/registrar")
	 public String mostrarFormulario(Model model) {
	     VentaModelDTO ventaDTO = new VentaModelDTO(); 

	     model.addAttribute("venta", ventaDTO);
	     model.addAttribute("productos", productoUseCase.listarProductos());
	     model.addAttribute("usuarios", usuarioUseCase.listarUsuarios());

	     return "ventas/registrar"; 
	 }
	 
	 @PostMapping("/guardar")
	 public String guardarVenta(@ModelAttribute("venta") VentaModelDTO ventaModel) {
	     ventaUseCase.registrarVenta(ventaModel);
	     return "redirect:/ventas";
	 }
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
	 
	 @PostMapping("/eliminar")
	 public String eliminarVenta(@RequestParam("id") Long id) {
	     ventaUseCase.eliminarVenta(id);
	     return "redirect:/ventas";
	 }
	 
}
