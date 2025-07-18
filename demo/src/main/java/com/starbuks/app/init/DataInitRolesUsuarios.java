package com.starbuks.app.init;

import com.starbuks.app.entitys.bean.Rol;
import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.persistence.RolRepository;
import com.starbuks.app.persistence.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitRolesUsuarios {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final BCryptPasswordEncoder passwordEncoder; // <- inyectado

    @PostConstruct
    @Transactional
    public void init() {
        if (rolRepository.count() == 0 && usuarioRepository.count() == 0) {
            Rol rolAdmin = new Rol();
            rolAdmin.setNombre("ADMIN");
            rolAdmin.setDescripcion("Personal trabajador del sistema");

            Rol rolUser = new Rol();
            rolUser.setNombre("USER");
            rolUser.setDescripcion("Usuario común");

            rolAdmin = rolRepository.save(rolAdmin);
            rolUser = rolRepository.save(rolUser);

            Usuario usuario1 = Usuario.builder()
                    .nombres("Hector")
                    .apellidos("Hernandez")
                    .email("hector@gmail.com")
                    .telefono("987654321")
                    .username("hector")
                    .password(passwordEncoder.encode("123"))
                    .activo(true)
                    .rol(rolAdmin)
                    .build();

            Usuario usuario2 = Usuario.builder()
                    .nombres("Jhon")
                    .apellidos("Doe")
                    .email("doe@gmail.com")
                    .telefono("912345678")
                    .username("doe")
                    .password(passwordEncoder.encode("abcdef"))
                    .activo(true)
                    .rol(rolUser)
                    .build();

            Usuario usuarioDemo = Usuario.builder()
                    .id(1L)
                    .nombres("Cliente Demo")
                    .apellidos("De Prueba")
                    .email("demo@correo.com")
                    .telefono("999999999")
                    .username("demo")
                    .password(passwordEncoder.encode("demo"))
                    .activo(true)
                    .rol(rolUser)
                    .build();

            Usuario usuario4 = Usuario.builder()
                    .nombres("Hector")
                    .apellidos("Hernandez")
                    .email("hector@gmail.com")
                    .telefono("987654321")
                    .username("hector")
                    .password(passwordEncoder.encode("123"))
                    .activo(true)
                    .rol(rolAdmin)
                    .build();

            usuarioRepository.save(usuario1);
            usuarioRepository.save(usuario2);
            usuarioRepository.save(usuarioDemo);
            usuarioRepository.save(usuario4);

            System.out.println("🟢 Roles y Usuarios insertados con contraseñas encriptadas.");
        }
    }
}
