package com.starbuks.app.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarritoCompraDTO {
    private Long usuarioId;
    private List<ItemCarritoDTO> items;
    private BigDecimal total;
    private int cantidadTotalItems;

}
