package com.starbuks.app.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetalleVentaDTO {
    private Long productoId;
    private int cantidad;
    private BigDecimal precioUnitario;
}
