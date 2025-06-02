package com.starbuks.app.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starbuks.app.entitys.bean.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);    
    List<Usuario> findByRol_Nombre(String nombreRol);
    long countByRol_Id(Long rolId);

}