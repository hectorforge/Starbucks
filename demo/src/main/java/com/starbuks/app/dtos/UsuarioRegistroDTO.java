package com.starbuks.app.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegistroDTO {
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String username;
    private String password;
}
