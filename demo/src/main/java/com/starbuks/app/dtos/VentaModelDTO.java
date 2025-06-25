package com.starbuks.app.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class VentaModelDTO {
    private Long usuarioId;
    private Long productoId;
    private int cantidad;
    private BigDecimal precioUnitario;
    private String modalidad;
}
