package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("mensaje", "Has cerrado sesión exitosamente");
        }
        return "login";
    }

//    @GetMapping("/dashboard")
//    public String dashboard(Authentication auth, Model model) {
//        model.addAttribute("usuario", auth.getName());
//
//        return "dash/dashboard"; // dashboard.html
//    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Usuario usuario = userDetails.getUsuario();

        model.addAttribute("usuario", usuario);
        model.addAttribute("rol", userDetails.getAuthorities());

        return "dash/dashboard";
    }

}
