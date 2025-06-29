package com.starbuks.app.entitys.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    @Column(nullable = false)
    private Integer cantidad;
    @Column(nullable = false)
    private BigDecimal precioUnitario;
    @Column(nullable = false)
    private BigDecimal subTotal;
}