package com.starbuks.app.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.persistence.ProductoRepository;
import com.starbuks.app.usecase.ProductoUseCase;

@Service
public class ProductoModel implements ProductoUseCase {

	 @Autowired
	    private ProductoRepository productoRepository;

	    @Override
	    public List<Producto> findAll() {
	        return productoRepository.findAll();
	    }

	    @Override
	    public List<Producto> findByActivoTrue() {
	        return productoRepository.findByActivoTrue();
	    }

	    @Override
	    public Optional<Producto> findByIdAndActivoTrue(Long id) {
	        return productoRepository.findByIdAndActivoTrue(id);
	    }

	    @Override
	    public List<Producto> findByNombreContainingIgnoreCase(String nombre) {
	        return productoRepository.findByNombreContainingIgnoreCase(nombre);
	    }

	    @Override
	    public List<Producto> findByPrecioBetween(BigDecimal min, BigDecimal max) {
	        return productoRepository.findByPrecioBetween(min, max);
	    }

	    @Override
	    public List<Producto> findByStockGreaterThanEqual(int cantidad) {
	        return productoRepository.findByStockGreaterThanEqual(cantidad);
	    }

	    @Override
	    public Optional<Producto> findById(Long id) {
	        return productoRepository.findById(id);
	    }

	    public void deleteById(Long id) {
	        productoRepository.deleteById(id);
	    }
	    
	    @Override
	    public Producto save(Producto producto) {
	        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
	            throw new IllegalArgumentException("El nombre del producto es obligatorio");
	        }

	        if (producto.getId() == null) {
	            producto.setActivo(true);
	            producto.setFechaEntrada(LocalDateTime.now());
	        }

	        return productoRepository.save(producto);
	    }

		@Override
		public Producto update(Long id, Producto producto) {
			Producto productoExistente = productoRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

			productoExistente.setNombre(producto.getNombre());
			productoExistente.setDescripcion(producto.getDescripcion());
			productoExistente.setPrecio(producto.getPrecio());
			productoExistente.setStock(producto.getStock());
			productoExistente.setActivo(producto.getActivo());
			productoExistente.setCodigo(producto.getCodigo());
			productoExistente.setImagenUrl(producto.getImagenUrl());
			productoExistente.setUnidadMedida(producto.getUnidadMedida());
			productoExistente.setPeso(producto.getPeso());
			productoExistente.setCategoriaId(producto.getCategoriaId());
			productoExistente.setFechaActualizacion(LocalDateTime.now()); // Opcional

			return productoRepository.save(productoExistente);
		}

		public List<Producto> listarActivos() {
	        return productoRepository.findByActivoTrue();
	    }
		
		@Override
	    public List<Producto> findByCategoriaActiva(Long categoriaId) {
	        return productoRepository.findByCategoriaId_IdAndActivoTrue(categoriaId);
	    }
}
