package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.model.MedicosCentroPaciente;
import medicaldate.repository.MedicosCentroPacienteRepository;

@Service
public class MedicosCentroPacienteService {
	
	private  MedicosCentroPacienteRepository medicosCentroPacienteRepository;

	public MedicosCentroPacienteService(@Autowired MedicosCentroPacienteRepository medicosCentroPacienteRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.medicosCentroPacienteRepository = medicosCentroPacienteRepository;

	}
	
	public List<MedicosCentroPaciente> getAll(){
		return (List<MedicosCentroPaciente>) medicosCentroPacienteRepository.findAll();
	}

}
