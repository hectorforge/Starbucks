package com.starbuks.app.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starbuks.app.entitys.bean.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

public List<Usuario> findByNombreStartingWith(String nombre);
	
	public Usuario getById(Long Id);
	
	public List<Usuario> findByEmail(String email);
	
	public List<Usuario> findByDescripcion(String descripcion);

	public List<Usuario> ListarUsuario();
}
