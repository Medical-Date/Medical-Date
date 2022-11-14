package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import medicaldate.model.Centros;
import medicaldate.model.Medico;
import medicaldate.model.MedicosCentro;
import medicaldate.model.Paciente;
@Repository
public interface MedicosCentroRepository extends CrudRepository<MedicosCentro, Long>{
	
	@Query("SELECT m FROM MedicosCentro m WHERE m.idCentro.id = ?1")
	public List<MedicosCentro> obtenerMedicoPorCentro(@Param("id") long id);
	
	
	@Query("SELECT m FROM MedicosCentro m WHERE m.idMedico.id = ?1")
	public MedicosCentro obtenerMedico(@Param("id") long id);
	
	

}
