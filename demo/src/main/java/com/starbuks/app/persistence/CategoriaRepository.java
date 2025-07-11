package com.starbuks.app.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbuks.app.entitys.bean.Categoria;
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	public List<Categoria> findByActivoTrue();
    Categoria findByNombre(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
    
}
