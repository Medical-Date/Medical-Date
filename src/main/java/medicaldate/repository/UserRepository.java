package medicaldate.repository;

import org.springframework.stereotype.Repository;

import medicaldate.model.Paciente;
import medicaldate.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.userName = :userName")
	public User obtenerUsuarioPorNombreUsuario(@Param("userName") String userName);
	
	@Query("SELECT firstName FROM User")
	public List<User> obtenerListaUsuariosPorNombre();
	
	

}
