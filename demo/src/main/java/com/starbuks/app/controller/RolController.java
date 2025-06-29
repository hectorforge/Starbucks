package com.starbuks.app.controller;

import java.util.List;

import com.starbuks.app.entitys.bean.Rol;

import com.starbuks.app.usecase.RolUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rol")
public class RolController {

	@Autowired
	private RolUseCase rolUseCase;
	
	@GetMapping("/acceso")
	public String acceso() {
		return "Bienvenido al software de gestion de venta";
	}
	
	@GetMapping("/listar")
	public List<Rol> ListarRoles(){
		return rolUseCase.listarRoles();
	}
} 
