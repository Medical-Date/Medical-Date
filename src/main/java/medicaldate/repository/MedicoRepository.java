package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import medicaldate.model.Medico;
import medicaldate.model.Paciente;

@Repository
public interface MedicoRepository extends CrudRepository<Medico, Long>{
	
	
	@Query("SELECT nombre FROM Medico")
	public List<Medico> obtenerListaMedicoPorNombre();
	
	@Query("SELECT m FROM Medico m WHERE m.nombre = ?1")
	public Medico obtenerMedicoPorNombre(@Param("nombre") String nombre);
	
	@Query("SELECT nombre FROM Medico m WHERE m.esAsignado != 1")
	public List<Medico> obtenerListaMedicoPorNombreNoAsignados();
	

}
