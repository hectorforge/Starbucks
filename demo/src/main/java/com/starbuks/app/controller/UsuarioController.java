package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.usecase.UsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase usuarioServicio;

    // CRUD
    @GetMapping
    public List<Usuario> listar() {
        return usuarioServicio.listar();
    }

    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioServicio.obtenerPorId(id);
    }

    @PostMapping
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioServicio.registrar(usuario);
    }

    @PutMapping
    public Usuario actualizar(@RequestBody Usuario usuario) {
        return usuarioServicio.actualizar(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioServicio.eliminar(id);
    }

    // ADDS
    @GetMapping("/username")
    public Usuario buscarPorUsername(@RequestParam String username) {
        return usuarioServicio.buscarPorUsername(username);
    }

    @GetMapping("/rol")
    public List<Usuario> listarPorRol(@RequestParam String rol) {
        return usuarioServicio.listarPorRol(rol);
    }

    @PutMapping("/{id}/password")
    public void actualizarPassword(@PathVariable Long id, @RequestParam String nuevaPassword) {
        usuarioServicio.actualizarPassword(id, nuevaPassword);
    }
}