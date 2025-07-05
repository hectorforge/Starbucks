package com.starbuks.app.security;

import com.starbuks.app.entitys.bean.Usuario;
import com.starbuks.app.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Usuario u = usuarioRepository.findByUsernameAndActivoTrue(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado o inactivo"));
      // mapea Usuario → UserDetails (CustomUserDetails)
      return new CustomUserDetails(u);
    }
}
