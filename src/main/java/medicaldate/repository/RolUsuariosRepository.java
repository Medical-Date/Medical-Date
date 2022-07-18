package medicaldate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.Rol;
import medicaldate.model.RolUsuarios;

@Repository
public interface RolUsuariosRepository extends CrudRepository<RolUsuarios, Long> {

}
