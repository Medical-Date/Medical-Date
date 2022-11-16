package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
	
	public MedicosCentroPaciente obtenerMedicoCentroPacientePorPaciente(long id) {
		return medicosCentroPacienteRepository.obtenerMedicoCentroPacientePorPaciente(id);
	}
	
	public List<MedicosCentroPaciente> obtenerListPacientePorMedico(long id){
		return medicosCentroPacienteRepository.obtenerListPacientePorMedico(id);
	}
	
	public MedicosCentroPaciente obtenerMedicoCentroPacientePorMedico(long id) {
		return medicosCentroPacienteRepository.obtenerMedicoCentroPacientePorMedico(id);
	}
	
	public List<MedicosCentroPaciente> obtenerListPacientePorMedicoSinHistorial(long id){
		return medicosCentroPacienteRepository.obtenerListPacientePorMedicoSinHistorial(id);
	}

}
