package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import medicaldate.model.Medico;
import medicaldate.model.Paciente;
import medicaldate.model.User;
@Repository
public interface PacienteRepository extends CrudRepository<Paciente, Long>{
	
	@Query("SELECT nombre FROM Paciente")
	public List<Paciente> obtenerListaPacientePorNombre();
	
	@Query("SELECT p FROM Paciente p WHERE p.nombre = ?1")
	public Paciente obtenerPacientePorNombre(@Param("nombre") String nombre);

}
