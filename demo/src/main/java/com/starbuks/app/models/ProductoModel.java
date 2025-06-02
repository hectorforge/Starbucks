package com.starbuks.app.models;

import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.persistence.ProductoRepository;
import com.starbuks.app.usecase.ProductoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoModel implements ProductoUseCase {

    private final ProductoRepository _productoRepository;

    @Override
    public List<Producto> listar() {
        return _productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return _productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto registrar(Producto producto) {
        return _productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Producto producto) {
        return _productoRepository.save(producto);
    }

    @Override
    public void eliminar(Long id) {
        _productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> buscarPorRangoPrecio(Double precioMin, Double precioMax) {
        return _productoRepository.findByPrecioBetween(
            BigDecimal.valueOf(precioMin), BigDecimal.valueOf(precioMax)
        );
    }

    @Override
    public List<Producto> buscarPorUnidadMedida(String unidadMedida) {
        return _productoRepository.findByUnidad_medida(unidadMedida);
    }

    @Override
    public List<Producto> listarPorCategoria(Long categoriaId) {
        return _productoRepository.findByCategoriaId_Id(categoriaId);
    }
}