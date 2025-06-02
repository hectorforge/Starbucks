package emp.cafeteria.usecase;

import java.util.List;

import emp.cafeteria.entity.bean.Rol;
import org.springframework.stereotype.Component;

@Component
public interface RolUseCase {

	
	public List<Rol> findByDescripcion(String descripcion);
	
	public List<Rol> ListarRoles();
	
	public Integer ActualizarRol(String descripcion, Integer idRol);
}




