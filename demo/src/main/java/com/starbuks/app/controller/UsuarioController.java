package com.starbuks.app.controller;

import com.starbuks.app.dtos.UsuarioDTO;
import com.starbuks.app.entitys.bean.Rol;
import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.usecase.RolUseCase;
import com.starbuks.app.usecase.UsuarioUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioUseCase usuarioUseCase;
	private final RolUseCase rolUseCase;

	// LISTAR TODOS LOS USUARIOS
	@GetMapping
	public String listarUsuarios(Model model) {
		List<Usuario> usuarios = usuarioUseCase.listarUsuarios();
		model.addAttribute("usuarios", usuarios);
		return "usuario/listar";
	}

	// FORMULARIO PARA NUEVO USUARIO
	@GetMapping("/nuevo")
	public String mostrarFormularioNuevo(Model model) {
		model.addAttribute("usuario", new UsuarioDTO());
		model.addAttribute("usuarioId", null);
		model.addAttribute("roles", rolUseCase.listarRoles());
		return "usuario/formulario";
	}

	// GUARDAR USUARIO NUEVO
	@PostMapping("/guardar")
	public String guardarUsuario(@ModelAttribute UsuarioDTO dto) {
		Usuario usuario = convertirDtoAEntidad(dto);
		usuarioUseCase.registrarUsuario(usuario);
		return "redirect:/usuarios";
	}

	// FORMULARIO PARA EDITAR USUARIO
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
		Usuario usuario = usuarioUseCase.buscarPorId(id).orElseThrow();

		UsuarioDTO dto = new UsuarioDTO();
		dto.setNombres(usuario.getNombres());
		dto.setApellidos(usuario.getApellidos());
		dto.setEmail(usuario.getEmail());
		dto.setTelefono(usuario.getTelefono());
		dto.setUsername(usuario.getUsername());
		dto.setPassword(usuario.getPassword());
		dto.setActivo(usuario.getActivo());

		if (usuario.getRol() != null) {
			dto.setRolId(usuario.getRol().getId());
		}

		model.addAttribute("usuario", dto);
		model.addAttribute("usuarioId", id);
		model.addAttribute("roles", rolUseCase.listarRoles());
		return "usuario/formulario";
	}

	// ACTUALIZAR USUARIO EXISTENTE
	@PostMapping("/actualizar/{id}")
	public String actualizarUsuario(@PathVariable Long id, @ModelAttribute UsuarioDTO dto) {
		Usuario actualizado = convertirDtoAEntidad(dto, id);
		usuarioUseCase.actualizarUsuario(id, actualizado);
		return "redirect:/usuarios";
	}

	// ELIMINAR USUARIO
	@PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes attrs) {
        usuarioUseCase.eliminarPorId(id);
        attrs.addFlashAttribute("mensajeExito", "Usuario eliminado");
        return "redirect:/usuarios";
    }

	// Convertir DTO a Entidad con ID (para editar)
	private Usuario convertirDtoAEntidad(UsuarioDTO dto, Long id) {
		Usuario usuario = convertirDtoAEntidad(dto);
		usuario.setId(id);
		return usuario;
	}

	// Convertir DTO a Entidad (común para guardar/editar)
	private Usuario convertirDtoAEntidad(UsuarioDTO dto) {
		Usuario u = new Usuario();
		u.setNombres(dto.getNombres());
		u.setApellidos(dto.getApellidos());
		u.setEmail(dto.getEmail());
		u.setTelefono(dto.getTelefono());
		u.setUsername(dto.getUsername());
		u.setPassword(dto.getPassword());
		u.setActivo(dto.getActivo());

		if (dto.getRolId() != null) {
			Rol rol = rolUseCase.obtenerPorId(dto.getRolId());
			u.setRol(rol);
		}

		return u;
	}
}
