package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import medicaldate.model.Cita;
import medicaldate.model.User;

@Repository
public interface CitaRepository extends CrudRepository<Cita, Long>{
	
	
	@Query("SELECT  c FROM Cita c WHERE c.medico.user.id = ?1")
	List<Cita> getListaCitasByUsuario(@Param("id") Long id);
	
	@Query("SELECT  c FROM Cita c WHERE c.medico.id = ?1")
	List<Cita> getListaCitasMedicoByPaciente(@Param("id") Long id);
	
	@Query("SELECT  c FROM Cita c WHERE c.paciente.id = ?1")
	List<Cita> getListaCitasByPaciente(@Param("id") Long id);
	
	
	

}
