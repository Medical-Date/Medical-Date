package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import medicaldate.model.SolicitudesRegistros;
import medicaldate.model.User;

@Repository
public interface SolicitudesRegistrosRepository extends CrudRepository<SolicitudesRegistros, Long>{
	
	@Query("SELECT s FROM SolicitudesRegistros s WHERE s.estado = 0")
	public List<SolicitudesRegistros> listaSolicitudesRechazadas();
	
	@Query("SELECT s FROM SolicitudesRegistros s WHERE s.estado is null")
	public List<SolicitudesRegistros> listaSolicitudesPendientes();
	
	@Query("SELECT s FROM SolicitudesRegistros s WHERE s.estado = 1")
	public List<SolicitudesRegistros> listaSolicitudesAceptadas();
	
	@Query("SELECT s FROM SolicitudesRegistros s WHERE s.user.id =?1")
	public SolicitudesRegistros comprobarSiExisteSolicitudPorUsuario(@Param("id") Long id);

}
