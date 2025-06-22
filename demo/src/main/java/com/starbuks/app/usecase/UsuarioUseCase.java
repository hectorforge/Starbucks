package com.starbuks.app.usecase;

import java.util.List;

import org.springframework.stereotype.Component;
import com.starbuks.app.entitys.bean.Usuario;

@Component
public interface UsuarioUseCase {

	List<Usuario> listarUsuarios();

    List<Usuario> findByEmail(String email);

    List<Usuario> getById(String id);

    List<Usuario> findByNombreStartingWith(String nombre);

    Integer actualizarUsuario(String nombre, Integer idUsuario);

}
