package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.SolicitudesRegistros;

@Repository
public interface SolicitudesRegistrosRepository extends CrudRepository<SolicitudesRegistros, Long>{

}
