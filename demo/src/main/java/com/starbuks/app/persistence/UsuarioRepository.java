package com.starbuks.app.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starbuks.app.entitys.bean.Usuario;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

public List<Usuario> findByNombresStartingWith(String nombre);
	
	public Usuario getById(Long Id);
	
	public List<Usuario> findByEmail(String email);

	@Query("SELECT u FROM Usuario u")
	public List<Usuario> ListarUsuario();

	Optional<Usuario> findByUsername(String username);
	
	Optional<Usuario> findByUsernameAndActivoTrue(String username);
}
