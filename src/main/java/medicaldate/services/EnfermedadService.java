package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.model.Enfermedad;
import medicaldate.repository.EnfermedadRepository;

@Service
public class EnfermedadService {

	private EnfermedadRepository enfermedadRepository;

	public EnfermedadService(@Autowired EnfermedadRepository enfermedadRepository) {
		super();
		this.enfermedadRepository = enfermedadRepository;
	}

	public List<Enfermedad> getEnfermedades() {
		return (List<Enfermedad>) enfermedadRepository.findAll();
	}

	public Enfermedad getEnfermedadById(Long id) {
		return enfermedadRepository.findById(id).get();
	}

}
