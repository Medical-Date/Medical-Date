package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.MedicosCentro;
import medicaldate.model.SolicitudesCambioMedico;

@Repository
public interface SolicitudesCambioMedicoRepository extends CrudRepository<SolicitudesCambioMedico, Long>{

}
