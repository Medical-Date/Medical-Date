package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.model.Cita;
import medicaldate.repository.CitaRepository;

@Service
public class CitaService {
	
	private CitaRepository citaRepository;

	public CitaService(@Autowired CitaRepository citaRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.citaRepository = citaRepository;

	}
	
	public List<Cita> getCitas(){
		return (List<Cita>) citaRepository.findAll();
	}

}
