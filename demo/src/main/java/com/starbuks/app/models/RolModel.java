package com.starbuks.app.models;

import com.starbuks.app.entitys.bean.Rol;
import com.starbuks.app.persistence.RolRepository;
import com.starbuks.app.persistence.UsuarioRepository;
import com.starbuks.app.usecase.RolUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolModel implements RolUseCase {

    private final RolRepository _rolRepository;
    private final UsuarioRepository _usuarioRepository;

    @Override
    public List<Rol> listar() {
        return _rolRepository.findAll();
    }

    @Override
    public Rol obtenerPorId(Long id) {
        return _rolRepository.findById(id).orElse(null);
    }

    @Override
    public Rol registrar(Rol rol) {
        return _rolRepository.save(rol);
    }

    @Override
    public Rol actualizar(Rol rol) {
        return _rolRepository.save(rol);
    }

    @Override
    public void eliminar(Long id) {
        _rolRepository.deleteById(id);
    }

    @Override
    public Rol obtenerPorNombre(String nombreRol) {
        return _rolRepository.findByNombre(nombreRol).orElse(null);
    }

    @Override
    public boolean esRolAdmin(Long rolId) {
        Rol rol = obtenerPorId(rolId);
        return rol != null && rol.getNombre().name().equalsIgnoreCase("ADMIN");
    }

    @Override
    public long contarUsuariosPorRol(Long rolId) {
        return _usuarioRepository.countByRol_Id(rolId);
    }
}