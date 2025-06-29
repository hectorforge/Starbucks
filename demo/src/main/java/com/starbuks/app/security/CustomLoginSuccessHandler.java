package com.starbuks.app.security;

import com.starbuks.app.entitys.bean.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {
        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        String rol = usuario.getRol().getNombre();

        if ("USER".equalsIgnoreCase(rol)) {
            response.sendRedirect("/cliente/inicio");
        } else {
            response.sendRedirect("/dashboard");
        }
    }
}
