package medicaldate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.repository.SolicitudesRegistrosRepository;

@Service
public class SolicitudesRegistrosService {
	
private SolicitudesRegistrosRepository solicitudesRegistrosRepository;
	
	public SolicitudesRegistrosService(	@Autowired SolicitudesRegistrosRepository solicitudesRegistrosRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.solicitudesRegistrosRepository = solicitudesRegistrosRepository;
	}

}
