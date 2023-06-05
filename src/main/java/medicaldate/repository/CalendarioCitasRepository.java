package medicaldate.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import medicaldate.model.CalendarioCitas;

@Repository
public interface CalendarioCitasRepository extends CrudRepository<CalendarioCitas, Long>{
	
	@Query("SELECT c FROM CalendarioCitas c WHERE c.idCitas.id = ?1")
	public CalendarioCitas obtenerCalendarioPorCita(@Param("id") long id);

}
