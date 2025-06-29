package com.starbuks.app.usecase;

import com.starbuks.app.entitys.bean.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioUseCase {

    List<Usuario> listarUsuarios();
    Optional<Usuario> buscarPorId(Long id);
    Usuario registrarUsuario(Usuario usuario);
    Usuario actualizarUsuario(Long id, Usuario usuario);
    void eliminarPorId(Long id);
}
