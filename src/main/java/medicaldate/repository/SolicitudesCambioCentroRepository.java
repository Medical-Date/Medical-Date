package medicaldate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import medicaldate.model.SolicitudesCambioCentro;

@Repository
public interface SolicitudesCambioCentroRepository extends CrudRepository<SolicitudesCambioCentro, Long> {

    @Query("SELECT s FROM SolicitudesCambioCentro s WHERE s.estado is null")
    public List<SolicitudesCambioCentro> listaSolicitudesCambioCentroPendientes();

    @Query("SELECT s FROM SolicitudesCambioCentro s WHERE s.estado = 1")
    public List<SolicitudesCambioCentro> listaSolicitudesCambioCentroAceptadas();

    @Query("SELECT s FROM SolicitudesCambioCentro s WHERE s.estado = 0")
    public List<SolicitudesCambioCentro> listaSolicitudesCambioCentroRechazadas();

}