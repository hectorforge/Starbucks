package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute
    public void agregarAtributosGlobales(Model model, Authentication auth) {
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            Usuario usuario = userDetails.getUsuario();
            model.addAttribute("usuario", usuario);
            model.addAttribute("rol", userDetails.getAuthorities());
        }
    }
}