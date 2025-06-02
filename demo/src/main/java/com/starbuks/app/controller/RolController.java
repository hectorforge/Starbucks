package com.starbuks.app.controller;

import com.starbuks.app.entitys.bean.Rol;
import com.starbuks.app.usecase.RolUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rol")
@RequiredArgsConstructor
public class RolController {

    private final RolUseCase rolServicio;

    // CRUD
    @GetMapping
    public List<Rol> listar() {
        return rolServicio.listar();
    }

    @GetMapping("/{id}")
    public Rol obtenerPorId(@PathVariable Long id) {
        return rolServicio.obtenerPorId(id);
    }

    @PostMapping
    public Rol registrar(@RequestBody Rol rol) {
        return rolServicio.registrar(rol);
    }

    @PutMapping
    public Rol actualizar(@RequestBody Rol rol) {
        return rolServicio.actualizar(rol);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        rolServicio.eliminar(id);
    }

    // ADDS
    @GetMapping("/nombre")
    public Rol obtenerPorNombre(@RequestParam String nombre) {
        return rolServicio.obtenerPorNombre(nombre);
    }

    @GetMapping("/{id}/admin")
    public boolean esRolAdmin(@PathVariable Long id) {
        return rolServicio.esRolAdmin(id);
    }

    @GetMapping("/{id}/usuarios/count")
    public long contarUsuariosPorRol(@PathVariable Long id) {
        return rolServicio.contarUsuariosPorRol(id);
    }
}
