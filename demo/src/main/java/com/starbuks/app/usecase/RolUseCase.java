package com.starbuks.app.usecase;

import java.util.List;

import com.starbuks.app.entitys.bean.Rol;
import org.springframework.stereotype.Component;

@Component
public interface RolUseCase {

public Rol findById(Long id);
	
	public List<Rol> ListarRoles();

}
