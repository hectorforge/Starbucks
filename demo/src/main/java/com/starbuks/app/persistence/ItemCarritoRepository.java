package com.starbuks.app.persistence;

import com.starbuks.app.entitys.bean.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
}