package emp.cafeteria.persistence;

import java.util.List;

import emp.cafeteria.entity.bean.Rol;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {

	public Rol getById(Integer Id);
	
	public List<Rol> findByDescripcion(String descripcion);
	
	@Query("SELECT e FROM Rol e")
	public List<Rol> ListarRoles();
	
	@Transactional
	@Modifying
	@Query("UPDATE Rol e SET e.descripcion = :descripcion WHERE e.idRol = :idRol")
	public Rol ActualizarRol(@Param("descripcion")String descripcion, @Param("idRol") Integer idRol);
	
}
