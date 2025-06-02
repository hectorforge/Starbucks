package com.starbuks.app.persistence;

import com.starbuks.app.entitys.bean.Producto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {


    public List<Producto> findByActivoTrue();


    public Optional<Producto> findByIdAndActivoTrue(Long id);

    public List<Producto> findByNombreContainingIgnoreCase(String nombre);

    public List<Producto> findByPrecioBetween(BigDecimal min, BigDecimal max);

    public List<Producto> findByStockGreaterThanEqual(int cantidad);

    public void deleteById(Long id);

}
