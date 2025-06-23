package com.starbuks.app.entitys.bean;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private CarritoCompra carrito;
}
