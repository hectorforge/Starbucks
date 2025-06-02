package com.starbuks.app.entitys.bean;

import com.starbuks.app.entitys.enums.ERolList;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private ERolList nombre;

    @Column(name = "descripcion")
    private String descripcion;
}
