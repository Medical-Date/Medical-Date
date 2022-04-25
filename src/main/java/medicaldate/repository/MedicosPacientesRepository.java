package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.MedicosPacientes;

@Repository
public interface MedicosPacientesRepository  extends CrudRepository<MedicosPacientes, Long>{

}
