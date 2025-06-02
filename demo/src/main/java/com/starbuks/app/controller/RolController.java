package emp.cafeteria.controller;

import java.util.List;

import emp.cafeteria.entity.bean.Rol;

import emp.cafeteria.usecase.RolUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/","/cafeteria","/home"})
public class RolController {
	 
	@Autowired
	private RolUseCase rolUseCase;
	
	@GetMapping("/acceso")
	public String acceso() {
		return "Bienvenido al software de gestion de venta";
	}
	
	@GetMapping("/listar")
	public List<Rol> ListarRoles(){
		return rolUseCase.ListarRoles();
	}
	
	@PutMapping("/actualizar/{descripcion}/{idRol}")
	public Integer ActualizarRol(@PathVariable String descripcion, @PathVariable Integer idRol) {
		return rolUseCase.ActualizarRol(descripcion, idRol);
	}
	
	@PutMapping("/actualizarMensaje/{descripcion}/{idRol}")
	public ResponseEntity<String> ActualizarRolMensaje(@PathVariable String descripcion, @PathVariable Integer idRol){
		
		Integer Rol = rolUseCase.ActualizarRol(descripcion, idRol);
		
		if(Rol>0) {
			return ResponseEntity.ok("el rol del trabajador se registo con exito ..!");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El rol no existe: "+ idRol);
		}
	}

}
