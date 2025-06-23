package com.starbuks.app.persistence;

import java.util.List;
import java.util.Optional;

import com.starbuks.app.entitys.bean.Rol;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

	public Rol getById(Integer Id);
	
	@Query("SELECT e FROM Rol e")
	public List<Rol> ListarRoles();

	Optional<Rol> findByNombre(String nombre);
}
