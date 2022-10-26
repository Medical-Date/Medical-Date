package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


import medicaldate.model.MedicosPacientes;
import medicaldate.repository.MedicosPacientesRepository;

@Service
public class MedicosPacientesService {
	
	private MedicosPacientesRepository medicosPacientesRepository;

	public MedicosPacientesService(@Autowired MedicosPacientesRepository medicosPacientesRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.medicosPacientesRepository = medicosPacientesRepository;

	}
	
	public List<MedicosPacientes> getMedicosPacientes(){
		return (List<MedicosPacientes>) medicosPacientesRepository.findAll();
	}
	
	public MedicosPacientes getMedicoByPacienteLogado(Long id) {
		return medicosPacientesRepository.getMedicoByPacienteLogado(id);
	}

}
