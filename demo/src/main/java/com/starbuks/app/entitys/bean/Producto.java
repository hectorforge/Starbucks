package com.starbuks.app.entitys.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "stock")
    private int stock;
    @Column(name = "precio")
    private BigDecimal precio;
    
    @CreationTimestamp
    @Column(name = "fecha_entrada", updatable = false)
    private LocalDateTime fechaEntrada;
    
    @Column(name = "activo")
    private Boolean activo;
    
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "imagen_url")
    private String imagenUrl;
    
    @Column(name = "unidad_medida")
    private String unidadMedida;
    
    @Column(name = "peso")
    private Double peso;
    
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoriaId;

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", stock=" + stock +
                ", precio=" + precio +
                ", fechaEntrada=" + fechaEntrada +
                ", activo=" + activo +
                ", codigo='" + codigo + '\'' +
                ", imagenUrl='" + imagenUrl + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", peso=" + peso +
                ", fechaActualizacion=" + fechaActualizacion +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
