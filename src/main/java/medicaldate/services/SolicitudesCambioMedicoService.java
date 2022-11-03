package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.model.SolicitudesCambioMedico;
import medicaldate.repository.SolicitudesCambioMedicoRepository;
import medicaldate.repository.SolicitudesRegistrosRepository;

@Service
public class SolicitudesCambioMedicoService {
	
	private SolicitudesCambioMedicoRepository solicitudesCambioMedicoRepository;
	
	public SolicitudesCambioMedicoService(SolicitudesCambioMedicoRepository solicitudesCambioMedicoRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.solicitudesCambioMedicoRepository = solicitudesCambioMedicoRepository;
	}
	
	
	public List<SolicitudesCambioMedico> listaSolicitudesCambioMedicoRechazadas(){
		return solicitudesCambioMedicoRepository.listaSolicitudesCambioMedicoRechazadas();
	}
	
	public List<SolicitudesCambioMedico> listaSolicitudesCambioMedicoPendientes(){
		return solicitudesCambioMedicoRepository.listaSolicitudesCambioMedicoPendientes();
	}
	
	public List<SolicitudesCambioMedico> listaSolicitudesCambioMedicoAceptadas(){
		return solicitudesCambioMedicoRepository.listaSolicitudesCambioMedicoAceptadas();
	}

}
