package com.starbuks.app.persistence;

import com.starbuks.app.entitys.bean.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    List<CarritoItem> findByCarritoId(Long carritoId);
    void deleteByCarritoId(Long carritoId);
}
