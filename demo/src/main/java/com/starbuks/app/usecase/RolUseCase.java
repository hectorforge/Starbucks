package com.starbuks.app.usecase;

import java.util.List;

import com.starbuks.app.entitys.bean.Rol;
import org.springframework.stereotype.Component;

@Component
public interface RolUseCase {

public List<Rol> findByDescripcion(String descripcion);
	
	public List<Rol> ListarRoles();

}
