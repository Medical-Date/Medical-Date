package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.model.Cita;
import medicaldate.repository.CalendarioRepository;
import medicaldate.repository.CitaRepository;

@Service
public class CalendarioService {
	
	private CalendarioRepository calendarioRepository;

	public CalendarioService(@Autowired CalendarioRepository calendarioRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.calendarioRepository = calendarioRepository;

	}


}
