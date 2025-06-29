package com.starbuks.app.usecase;

import com.starbuks.app.dtos.UsuarioRegistroDTO;
import com.starbuks.app.entitys.bean.Usuario;
import org.springframework.stereotype.Component;

@Component
public interface UsuarioAuthUseCase {
    Usuario guardar(UsuarioRegistroDTO registroDTO);
}
