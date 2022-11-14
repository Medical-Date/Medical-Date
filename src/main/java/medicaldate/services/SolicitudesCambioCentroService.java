package medicaldate.services;

import java.util.List;

import org.springframework.stereotype.Service;

import medicaldate.model.SolicitudesCambioCentro;
import medicaldate.repository.SolicitudesCambioCentroRepository;

@Service
public class SolicitudesCambioCentroService {

private SolicitudesCambioCentroRepository solicitudesCambioCentroRepository;

    public SolicitudesCambioCentroService(SolicitudesCambioCentroRepository solicitudesCambioCentroRepository) {
        super();
        this.solicitudesCambioCentroRepository = solicitudesCambioCentroRepository;
    }

    public List<SolicitudesCambioCentro> listaSolicitudesCambioCentroPendientes(){
        return solicitudesCambioCentroRepository.listaSolicitudesCambioCentroPendientes();
    }

    public List<SolicitudesCambioCentro> listaSolicitudesCambioCentroAceptadas(){
        return solicitudesCambioCentroRepository.listaSolicitudesCambioCentroAceptadas();
    }

    public List<SolicitudesCambioCentro> listaSolicitudesCambioCentroRechazadas(){
        return solicitudesCambioCentroRepository.listaSolicitudesCambioCentroRechazadas();
    }

}