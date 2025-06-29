package com.starbuks.app.entitys.bean;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    private String direccionEntrega;
    private String estado; // EJ: PENDIENTE, EN CAMINO, ENTREGADO
}
