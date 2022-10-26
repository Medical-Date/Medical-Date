package medicaldate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.repository.CalendarioCitasRepository;
import medicaldate.repository.CalendarioRepository;
@Service
public class CalendarioCitasService {
	
	private CalendarioCitasRepository calendarioCitasRepository;

	public CalendarioCitasService(@Autowired CalendarioCitasRepository calendarioCitasRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.calendarioCitasRepository = calendarioCitasRepository;

	}

}
