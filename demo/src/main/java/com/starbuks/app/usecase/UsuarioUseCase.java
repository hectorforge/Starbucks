package com.starbuks.app.usecase;

import com.starbuks.app.entitys.bean.Usuario;
import java.util.List;

public interface UsuarioUseCase {

    // CRUD
    List<Usuario> listar();
    Usuario obtenerPorId(Long id);
    Usuario registrar(Usuario usuario);
    Usuario actualizar(Usuario usuario);
    void eliminar(Long id);

    // ADDS
    Usuario buscarPorUsername(String username);
    List<Usuario> listarPorRol(String nombreRol);
    void actualizarPassword(Long usuarioId, String nuevaPassword);
}
