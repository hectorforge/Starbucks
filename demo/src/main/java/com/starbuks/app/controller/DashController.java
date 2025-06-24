package com.starbuks.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dash")
@RequiredArgsConstructor
public class DashController {
    @GetMapping
    public String listarUsuarios(Model model) {
        return "dash/dashboard";
    }
}
