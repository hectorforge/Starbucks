package com.starbuks.app.models;

import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.persistence.UsuarioRepository;
import com.starbuks.app.usecase.UsuarioUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioModel implements UsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> findByNombreStartingWith(String nombre) {
    //    return usuarioRepository.findByNombresStartingWithIgnoreCase(nombre);
        return null;
    }

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        if (usuario.getUsername() == null || usuario.getUsername().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }

        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        if (usuario.getId() == null) {
            usuario.setActivo(true);
            usuario.setFechaRegistro(LocalDateTime.now());
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Long id, Usuario usuario) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        existente.setId(usuario.getId());
        existente.setNombres(usuario.getNombres());
        existente.setApellidos(usuario.getApellidos());
        existente.setEmail(usuario.getEmail());
        existente.setTelefono(usuario.getTelefono());
        existente.setUsername(usuario.getUsername());
        existente.setPassword(usuario.getPassword());
        existente.setActivo(usuario.getActivo());
        existente.setRol(usuario.getRol());
        existente.setFechaActualizacion(LocalDateTime.now());

        return usuarioRepository.save(existente);
    }

    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }
}