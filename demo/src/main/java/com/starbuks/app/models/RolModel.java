package com.starbuks.app.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starbuks.app.entitys.bean.Rol;
import com.starbuks.app.persistence.RolRepository;
import com.starbuks.app.usecase.RolUseCase;


@Service
public class RolModel implements RolUseCase {

	@Autowired
	private RolRepository rolRepository;
	
	@Override
	public List<Rol> listarRoles() {
		return rolRepository.ListarRoles();
	}

	@Override
	public Rol obtenerPorId(Long id) {
		return rolRepository.getById(id);
	}
}
