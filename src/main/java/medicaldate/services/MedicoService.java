package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.model.Medico;
import medicaldate.model.Paciente;
import medicaldate.repository.MedicoRepository;

@Service
public class MedicoService {

	private MedicoRepository medicoRepository;

	public MedicoService(@Autowired MedicoRepository medicoRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.medicoRepository = medicoRepository;

	}
	
	public List<Medico> getMedicos(){
		return (List<Medico>) medicoRepository.findAll();
	}
	
	public List<Medico> getListaMedicosPorNombre(){
		return medicoRepository.obtenerListaMedicoPorNombre();
	}
	
	public Medico getMedicosPorNombre(String nombre){
		return  medicoRepository.obtenerMedicoPorNombre(nombre);
	}
	

	
	
}
