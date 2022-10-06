package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.model.SolicitudesRegistros;
import medicaldate.repository.SolicitudesRegistrosRepository;

@Service
public class SolicitudesRegistrosService {
	
private SolicitudesRegistrosRepository solicitudesRegistrosRepository;
	
	public SolicitudesRegistrosService(	@Autowired SolicitudesRegistrosRepository solicitudesRegistrosRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.solicitudesRegistrosRepository = solicitudesRegistrosRepository;
	}
	
	public List<SolicitudesRegistros> getSolicitudesRegistros(){
		return (List<SolicitudesRegistros>) solicitudesRegistrosRepository.findAll();
	}
	
	public List<SolicitudesRegistros> listaSolicitudesRechazadas(){
		return solicitudesRegistrosRepository.listaSolicitudesRechazadas();
	}
	
	public List<SolicitudesRegistros> listaSolicitudesPendientes(){
		return solicitudesRegistrosRepository.listaSolicitudesPendientes();
	}
	
	public List<SolicitudesRegistros> listaSolicitudesAceptadas(){
		return solicitudesRegistrosRepository.listaSolicitudesAceptadas();
	}

}
