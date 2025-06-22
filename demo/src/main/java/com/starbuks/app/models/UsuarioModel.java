package com.startbucks.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startbucks.entitys.bean.Usuario;
import com.startbucks.persistence.UsuarioRepository;
import com.startbucks.usecase.UsuarioUseCase;

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
    public List<Usuario> getById(String id) {
        return usuarioRepository.getById(id);
    }

    @Override
    public List<Usuario> findByNombreStartingWith(String nombre) {
        return usuarioRepository.findByNombreStartingWith(nombre);
    }

    @Override
    public Integer actualizarUsuario(String nombre, Integer idUsuario) {
        return 1;
    }
}
