package com.startbucks.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.startbucks.entitys.bean.Rol;
import com.startbucks.persistence.RolRepository;
import com.startbucks.usecase.RolUseCase;


@Service
public class RolModel implements RolUseCase {

	@Autowired
	private RolRepository rolRepository;
	
	@Override
	public List<Rol> ListarRoles() {
		return rolRepository.ListarRoles();
	}

	@Override
	public List<Rol> findByDescripcion(String descripcion) {
		return rolRepository.findByDescripcion(descripcion);
	}

	@Override
	public Integer ActualizarRol(@PathVariable String descripcion, @PathVariable Integer idRol) {
		Rol result = rolRepository.ActualizarRol(descripcion, idRol);
		return result != null ? 1 : 0;
	}
}
