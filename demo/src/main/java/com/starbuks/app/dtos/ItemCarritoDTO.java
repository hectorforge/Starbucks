package com.starbuks.app.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemCarritoDTO {
    private Long productoId;
    private int cantidad;
    private Long usuarioId;
    private String nombreProducto;
    private BigDecimal precioUnitario;
    private BigDecimal subTotal;

    public BigDecimal getSubtotal() {
        if (precioUnitario == null) {
            return BigDecimal.ZERO;
        }
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}