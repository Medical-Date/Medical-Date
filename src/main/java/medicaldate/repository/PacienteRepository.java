package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.Paciente;
@Repository
public interface PacienteRepository extends CrudRepository<Paciente, Long>{

}
