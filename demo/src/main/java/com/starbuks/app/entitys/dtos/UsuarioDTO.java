package com.starbuks.app.entitys.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String username;
    private String password;
    private Boolean activo;
    private Long rolId;
}
