package medicaldate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.repository.EnfermedadTratamientosRepository;

@Service
public class EnfermedadTratamientosService {
	private EnfermedadTratamientosRepository enfermedadTratamientosRepository;

	public EnfermedadTratamientosService(@Autowired EnfermedadTratamientosRepository enfermedadTratamientosRepository) {
		super();
		this.enfermedadTratamientosRepository = enfermedadTratamientosRepository;
	}

}
