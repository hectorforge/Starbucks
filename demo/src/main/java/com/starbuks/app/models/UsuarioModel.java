package com.starbuks.app.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.persistence.UsuarioRepository;
import com.starbuks.app.usecase.UsuarioUseCase;

@Service
public class UsuarioModel implements UsuarioUseCase {

	@Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll(); 
    }

    @Override
    public List<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario getById(Long id) {
        return usuarioRepository.getById(id);
    }

    @Override
    public List<Usuario> findByNombreStartingWith(String nombre) {
        return usuarioRepository.findByNombresStartingWith(nombre);
    }

    @Override
    public Integer actualizarUsuario(String nombre, Integer idUsuario) {
        return 1;
    }
}
