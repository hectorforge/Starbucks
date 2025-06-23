package com.starbuks.app.persistence;

import com.starbuks.app.entitys.bean.CarritoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarritoCompraRepository extends JpaRepository<CarritoCompra, Long> {

    List<CarritoCompra> findByUsuarioIdAndPagadoFalse(Long usuarioId);
}
