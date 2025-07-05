package com.starbuks.app.controller;

import com.starbuks.app.service.ReporteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reportes")
public class ReporteController {
    private final ReporteService reporteService;

    /** 1️⃣ Devuelve la página Thymeleaf con los botones e iframe */
    @GetMapping
    public String verMenuReportes(Model model) {
        return "reportes/reportes";  // buscará src/main/resources/templates/reportes.html
    }

    /** 2️⃣ Descarga/abre el PDF de ventas dentro del iframe */
    @GetMapping("/ventas/pdf")
    public void descargarReporteVentas(HttpServletResponse resp) throws JRException, IOException {
        byte[] pdf = reporteService.generarReporteVentas();
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "inline; filename=ventas.pdf");
        resp.getOutputStream().write(pdf);
        resp.getOutputStream().flush();
    }

    /** 3️⃣ Descarga/abre el PDF de productos dentro del iframe */
    @GetMapping("/productos/pdf")
    public void descargarReporteProductos(HttpServletResponse resp) throws JRException, IOException {
        byte[] pdf = reporteService.generarReporteProductos();
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "inline; filename=productos.pdf");
        resp.getOutputStream().write(pdf);
        resp.getOutputStream().flush();
    }
}
