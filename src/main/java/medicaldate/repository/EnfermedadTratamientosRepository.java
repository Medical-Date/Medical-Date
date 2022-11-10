package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.EnfermedadTratamientos;

@Repository
public interface EnfermedadTratamientosRepository extends CrudRepository<EnfermedadTratamientos, Long>{

}
