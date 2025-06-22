package com.startbucks.usecase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.startbucks.entitys.bean.Producto;

public interface ProductoUseCase {

	public List<Producto> findAll();

    public List<Producto> findByActivoTrue();

    public Optional<Producto> findByIdAndActivoTrue(Long id);

    public List<Producto> findByNombreContainingIgnoreCase(String nombre);

    public List<Producto> findByPrecioBetween(BigDecimal min, BigDecimal max);

    public List<Producto> findByStockGreaterThanEqual(int cantidad);

    public Optional<Producto> findById(Long id);
    public Producto save(Producto producto);
    public Producto update(Long id, Producto producto);

    
    public void deleteById(Long id);
}
