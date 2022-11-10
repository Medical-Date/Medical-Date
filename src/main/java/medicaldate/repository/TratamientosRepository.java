package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import medicaldate.model.Cita;
import medicaldate.model.Tratamientos;

@Repository
public interface TratamientosRepository extends CrudRepository<Tratamientos, Long>{
	
	@Query("SELECT  t FROM Tratamientos t WHERE t.enfermedad.id = ?1")
	public List<Tratamientos> getTratamientosByEnfermedad(@Param("id") Long id);

}
