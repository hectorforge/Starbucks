package com.starbuks.app.usecase;

import com.starbuks.app.entitys.bean.Rol;
import java.util.List;

public interface RolUseCase {

    // CRUD
    List<Rol> listar();
    Rol obtenerPorId(Long id);
    Rol registrar(Rol rol);
    Rol actualizar(Rol rol);
    void eliminar(Long id);

    // ADDS
    Rol obtenerPorNombre(String nombreRol);
    boolean esRolAdmin(Long rolId);
    long contarUsuariosPorRol(Long rolId);
}
