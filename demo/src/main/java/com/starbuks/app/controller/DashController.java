package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dash")
@RequiredArgsConstructor
public class DashController {

    @GetMapping("/dash")
    public String dash(Model model, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        System.out.println("🔥🔥🔥🔥Este es el usuario :" + usuario);
        model.addAttribute("usuarioLogueado", usuario);
        return "dash/dashboard";
    }

}
