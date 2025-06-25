package com.starbuks.app.entitys.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Producto producto;

    private int cantidad;
    private BigDecimal precioUnitario;
    private LocalDateTime fecha;
    
    @Column(name = "modalidad")
    private String modalidad;
    
    public BigDecimal getTotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
    public String getFechaFormateada() {
        if (fecha == null) return "";
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
}

