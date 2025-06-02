package emp.cafeteria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emp.cafeteria.entity.bean.Usuario;
import emp.cafeteria.usecase.UsuarioUseCase;

@RestController
@RequestMapping({"/","/usuarios","/home"})
public class UsuarioController {
	
	@Autowired
	private UsuarioUseCase usuarioUseCase;
	
	@GetMapping("/acceso")
	public String acceso() {
		return "Bienvenido al software de gestion de venta";
	}
	
	 @GetMapping("/listar")
	    public List<Usuario> listarUsuarios() {
	        return usuarioUseCase.listarUsuarios();
	    }

	    @PutMapping("/actualizar/{nombre}/{idUsuario}")
	    public Integer actualizarUsuario(@PathVariable String nombre, @PathVariable Integer idUsuario) {
	        return usuarioUseCase.actualizarUsuario(nombre, idUsuario);
	    }

	    @PutMapping("/actualizarMensaje/{descripcion}/{idUsuario}")
	    public ResponseEntity<String> actualizarUsuarioMensaje(@PathVariable String descripcion, @PathVariable Integer idUsuario) {
	        Integer resultado = usuarioUseCase.actualizarUsuario(descripcion, idUsuario);
	        if (resultado > 0) {
	            return ResponseEntity.ok("El usuario se registró con éxito.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe: " + idUsuario);
	        }
	    }

	    @GetMapping("/buscar/email")
	    public List<Usuario> findByEmail(@RequestParam String email) {
	        return usuarioUseCase.findByEmail(email);
	    }

	    @GetMapping("/buscar/nombre")
	    public List<Usuario> findByNombreStartingWith(@RequestParam String nombre) {
	        return usuarioUseCase.findByNombreStartingWith(nombre);
	    }
}


