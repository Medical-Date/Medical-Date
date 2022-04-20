package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.Medico;

@Repository
public interface MedicoRepository extends CrudRepository<Medico, Long>{

}
