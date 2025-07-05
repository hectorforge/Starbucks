package com.starbuks.app.models;

import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.persistence.UsuarioRepository;
import com.starbuks.app.usecase.UsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioModel implements UsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;  // ← Inyectado

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // Validaciones
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (usuario.getUsername() == null || usuario.getUsername().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        // Sólo al crear
        if (usuario.getId() == null) {
            usuario.setActivo(true);
            usuario.setFechaRegistro(LocalDateTime.now());
        }

        // 🔐 Encriptar contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario existente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Actualizar campos
        existente.setNombres(usuario.getNombres());
        existente.setApellidos(usuario.getApellidos());
        existente.setEmail(usuario.getEmail());
        existente.setTelefono(usuario.getTelefono());
        existente.setUsername(usuario.getUsername());
        existente.setActivo(usuario.getActivo());
        existente.setRol(usuario.getRol());
        existente.setFechaActualizacion(LocalDateTime.now());

        // 🔐 Si la contraseña cambió (raw vs hashed), re-hashea
        String rawPass = usuario.getPassword();
        if (!passwordEncoder.matches(rawPass, existente.getPassword())) {
            existente.setPassword(passwordEncoder.encode(rawPass));
        }

        return usuarioRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        Usuario u = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setActivo(false);
        usuarioRepository.save(u);
    }
}
