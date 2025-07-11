package com.starbuks.app.persistence;

import com.starbuks.app.entitys.bean.Venta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {
	Page<Venta> findByUsuarioId(Long usuarioId, Pageable pageable);
    
    @Query("SELECT v FROM Venta v WHERE " +
    	       "LOWER(CONCAT(v.usuario.nombres, ' ', v.usuario.apellidos)) LIKE LOWER(CONCAT('%', :termino, '%'))")
    public List<Venta> buscarPorCliente(@Param("termino") String termino);
    
    @Query("SELECT DISTINCT v FROM Venta v JOIN v.detalles d WHERE LOWER(d.producto.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Venta> buscarPorProducto(@Param("termino") String termino);
    
    @Query("SELECT v FROM Venta v WHERE DATE(v.fecha) = :fecha")
    List<Venta> buscarPorFecha(@Param("fecha") java.time.LocalDate fecha);
    
    @Query("SELECT v FROM Venta v LEFT JOIN FETCH v.detalles d LEFT JOIN FETCH d.producto p LEFT JOIN FETCH v.usuario u")
    List<Venta> findAllConDetalles();
    
    @Query("""
    	      SELECT v
    	      FROM Venta v
    	      LEFT JOIN FETCH v.detalles d
    	      LEFT JOIN FETCH d.producto p
    	      LEFT JOIN FETCH v.usuario u
    	      WHERE v.id = :id
    	    """)
    	    Optional<Venta> findByIdConDetalles(@Param("id") Long id);
}