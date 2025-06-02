package com.starbuks.app.persistence;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbuks.app.entitys.bean.Categoria;
import com.starbuks.app.entitys.bean.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    List<Producto> findByUnidad_medida(String unidadMedida);
    List<Producto> findByCategoriaId_Id(Long categoriaId);
    long countByCategoriaId_Id(Long categoriaId);
}
