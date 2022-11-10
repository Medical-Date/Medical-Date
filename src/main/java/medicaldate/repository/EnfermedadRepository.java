package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import medicaldate.model.Cita;
import medicaldate.model.Enfermedad;

@Repository
public interface EnfermedadRepository extends CrudRepository<Enfermedad, Long> {
	
	@Query("SELECT  e FROM Enfermedad e WHERE e.nombre = ?1")
	public Enfermedad getEnfermedadByNombre(@Param("nombre") String nombre);


}
