package com.starbuks.app.usecase;

import com.starbuks.app.entitys.bean.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioUseCase {

    List<Usuario> listarUsuarios();

    Optional<Usuario> findById(Long id);

    List<Usuario> findByEmail(String email);

    List<Usuario> findByNombreStartingWith(String nombre);

    Usuario save(Usuario usuario);

    Usuario update(Long id, Usuario usuario);

    void deleteById(Long id);
}
