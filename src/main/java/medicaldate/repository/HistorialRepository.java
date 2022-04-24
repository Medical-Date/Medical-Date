package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.Historial;

@Repository
public interface HistorialRepository extends CrudRepository<Historial, Long> {

}
