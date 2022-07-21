package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.Centros;

@Repository
public interface CentrosRepository extends CrudRepository<Centros, Long>{

}
