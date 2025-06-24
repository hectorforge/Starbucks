package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    // LISTAR TODOS LAS VENTAS
    @GetMapping
    public String listarUsuarios(Model model) {
        return "ventas/listar";
    }
}
