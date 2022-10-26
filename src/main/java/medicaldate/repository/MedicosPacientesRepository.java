package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import medicaldate.model.Cita;
import medicaldate.model.MedicosPacientes;

@Repository
public interface MedicosPacientesRepository  extends CrudRepository<MedicosPacientes, Long>{
	
	
	@Query("SELECT mp FROM MedicosPacientes mp WHERE mp.idPaciente.id = ?1")
	public MedicosPacientes getMedicoByPacienteLogado(@Param("idPaciente") Long id);

}
