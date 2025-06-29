package com.starbuks.app.controller;

import com.starbuks.app.dtos.UsuarioRegistroDTO;
import com.starbuks.app.usecase.UsuarioAuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
@RequiredArgsConstructor
public class RegistroUsuarioController {
    private final UsuarioAuthUseCase usuarioAuthUseCase;

    // Agrega un atributo al modelo con un nuevo DTO para el formulario
    @ModelAttribute("usuario")
    public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
        return new UsuarioRegistroDTO();
    }

    // Muestra el formulario de registro
    @GetMapping
    public String mostrarFormularioRegistro(Model model) {
        return "registro"; // archivo registro.html en templates
    }

    // Procesa el formulario al hacer POST
    @PostMapping
    public String registrarCuentaUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO) {
        usuarioAuthUseCase.guardar(registroDTO);
        return "redirect:/registro?exito"; // redirige con mensaje de éxito
    }
}
