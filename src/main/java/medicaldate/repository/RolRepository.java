package medicaldate.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import medicaldate.model.Rol;

@Repository
public interface RolRepository extends CrudRepository<Rol, Long>{
	
	@Query("SELECT rol FROM Rol rol WHERE rol.rol = ?1")
	public Rol obtenerRolPorNombre(@Param("rol") String rol);
	
	@Query("SELECT rol FROM Rol rol WHERE rol.rol = 'ADMINISTRADOR'")
	public Rol obtenerRolAdmin();

}
