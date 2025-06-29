package com.starbuks.app.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class VentaModelDTO {
    private Long usuarioId;
    private String modalidad;
    
    private List<DetalleVentaDTO> detalles = new ArrayList<>();
}
