package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.Calendario;

@Repository
public interface CalendarioRepository extends CrudRepository<Calendario, Long>{

}
