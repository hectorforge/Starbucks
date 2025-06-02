package com.starbuks.app.models;

import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.persistence.UsuarioRepository;
import com.starbuks.app.usecase.UsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioModel implements UsuarioUseCase {

    private final UsuarioRepository _usuarioRepository;

    @Override
    public List<Usuario> listar() {
        return _usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return _usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        return _usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        return _usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Long id) {
        _usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return _usuarioRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<Usuario> listarPorRol(String nombreRol) {
        return _usuarioRepository.findByRol_Nombre(nombreRol);
    }

    @Override
    public void actualizarPassword(Long usuarioId, String nuevaPassword) {
        Usuario usuario = obtenerPorId(usuarioId);
        if (usuario != null) {
            usuario.setPassword(nuevaPassword);
            _usuarioRepository.save(usuario);
        }
    }
}
