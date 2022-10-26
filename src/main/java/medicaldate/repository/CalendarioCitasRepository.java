package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.Calendario;
import medicaldate.model.CalendarioCitas;

@Repository
public interface CalendarioCitasRepository extends CrudRepository<CalendarioCitas, Long>{

}
