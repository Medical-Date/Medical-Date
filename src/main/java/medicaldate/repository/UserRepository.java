package medicaldate.repository;

import org.springframework.stereotype.Repository;

import medicaldate.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.userName = ?1")
	public User obtenerUsuarioPorNombreUsuario(@Param("userName") String userName);
	
	

}
