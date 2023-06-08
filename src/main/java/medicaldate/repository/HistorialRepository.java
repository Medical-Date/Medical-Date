package medicaldate.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import medicaldate.model.Historial;
import medicaldate.model.MedicosCentroPaciente;

@Repository
public interface HistorialRepository extends CrudRepository<Historial, Long> {
	
	@Query("SELECT h FROM Historial h WHERE h.paciente.id = ?1")
	public Historial obtenerHistorialPorPaciente(@Param("id") long id);
	
	@Query("SELECT h FROM Historial h WHERE h.user.id = ?1")
	public Historial obtenerHistorialPorUsuario(@Param("id") long id);

}
