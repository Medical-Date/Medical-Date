package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import medicaldate.model.Centros;
import medicaldate.model.Medico;

@Repository
public interface CentrosRepository extends CrudRepository<Centros, Long>{
	
	@Query("SELECT nombre FROM Centros")
	public List<Centros> obtenerListaCentrosPorNombre();
	
	@Query("SELECT c FROM Centros c WHERE c.nombre = ?1")
	public Centros obtenerCentrosPorNombre(@Param("nombre") String nombre);

}
