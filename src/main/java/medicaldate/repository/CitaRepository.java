package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.Cita;

@Repository
public interface CitaRepository extends CrudRepository<Cita, Long>{

}
