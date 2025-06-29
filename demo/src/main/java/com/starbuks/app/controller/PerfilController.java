package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PerfilController {

    @GetMapping("/perfil")
    public String verPerfil(Authentication auth, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        model.addAttribute("usuario", usuario);
        return "dash/perfil";
    }
}
