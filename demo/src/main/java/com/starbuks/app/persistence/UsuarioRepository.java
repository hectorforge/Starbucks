package com.startbucks.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.startbucks.entitys.bean.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

public List<Usuario> findByNombreStartingWith(String nombre);
	
	public List<Usuario> getById(String Id);
	
	public List<Usuario> findByEmail(String email);
	
	public List<Usuario> findByDescripcion(String descripcion);

	public List<Usuario> ListarUsuario();
}
