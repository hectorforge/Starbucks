package com.starbuks.app.models;

import com.starbuks.app.dtos.UsuarioRegistroDTO;
import com.starbuks.app.entitys.bean.Rol;
import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.persistence.RolRepository;
import com.starbuks.app.persistence.UsuarioRepository;
import com.starbuks.app.usecase.UsuarioAuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioAuthServiceModel implements UsuarioAuthUseCase {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario guardar(UsuarioRegistroDTO dto) {
        Rol rol = rolRepository.findByNombre("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

        Usuario usuario = Usuario.builder()
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .activo(true)
                .rol(rol)
                .build();

        return usuarioRepository.save(usuario);
    }
}
