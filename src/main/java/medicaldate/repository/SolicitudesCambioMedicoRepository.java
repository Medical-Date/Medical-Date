package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.MedicosCentro;
import medicaldate.model.SolicitudesCambioMedico;
import medicaldate.model.SolicitudesRegistros;

@Repository
public interface SolicitudesCambioMedicoRepository extends CrudRepository<SolicitudesCambioMedico, Long>{
	
	@Query("SELECT s FROM SolicitudesCambioMedico s WHERE s.estado = 0")
	public List<SolicitudesCambioMedico> listaSolicitudesCambioMedicoRechazadas();
	
	@Query("SELECT s FROM SolicitudesCambioMedico s WHERE s.estado is null")
	public List<SolicitudesCambioMedico> listaSolicitudesCambioMedicoPendientes();
	
	@Query("SELECT s FROM SolicitudesCambioMedico s WHERE s.estado = 1")
	public List<SolicitudesCambioMedico> listaSolicitudesCambioMedicoAceptadas();

}
