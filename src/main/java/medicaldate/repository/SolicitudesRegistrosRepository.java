package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.SolicitudesRegistros;

@Repository
public interface SolicitudesRegistrosRepository extends CrudRepository<SolicitudesRegistros, Long>{
	
	@Query("SELECT s FROM SolicitudesRegistros s WHERE s.estado = 0")
	public List<SolicitudesRegistros> listaSolicitudesRechazadas();
	
	@Query("SELECT s FROM SolicitudesRegistros s WHERE s.estado is null")
	public List<SolicitudesRegistros> listaSolicitudesPendientes();
	
	@Query("SELECT s FROM SolicitudesRegistros s WHERE s.estado = 1")
	public List<SolicitudesRegistros> listaSolicitudesAceptadas();

}
