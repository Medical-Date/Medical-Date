package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.Centros;
import medicaldate.model.Medico;
import medicaldate.model.MedicosCentroPaciente;

@Repository
public interface MedicosCentroPacienteRepository extends CrudRepository<MedicosCentroPaciente, Long>{
	

}
