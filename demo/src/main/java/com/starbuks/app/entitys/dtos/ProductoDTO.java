package com.starbuks.app.entitys.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {
    private String nombre;
    private String descripcion;
    private int stock;
    private BigDecimal precio;
    private Boolean activo;
    private String codigo;
    private String imagenUrl;
    private String unidadMedida;
    private Double peso;
    private Long categoriaId;
}
