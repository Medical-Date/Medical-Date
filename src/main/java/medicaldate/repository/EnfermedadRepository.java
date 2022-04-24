package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.Enfermedad;

@Repository
public interface EnfermedadRepository extends CrudRepository<Enfermedad, Long> {

}
