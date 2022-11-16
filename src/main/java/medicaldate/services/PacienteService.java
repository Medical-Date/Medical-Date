package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import medicaldate.model.Paciente;
import medicaldate.repository.PacienteRepository;

@Service
public class PacienteService {
	
	private PacienteRepository pacienteRepository;

	public PacienteService(@Autowired PacienteRepository pacienteRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.pacienteRepository = pacienteRepository;

	}
	
	public List<Paciente> getPacientes(){
		return (List<Paciente>) pacienteRepository.findAll();
	}
	
	public List<Paciente> getListaPacientesPorNombre(){
		return  pacienteRepository.obtenerListaPacientePorNombre();
	}
	
	public Paciente getPacientesPorNombre(String nombre){
		return  pacienteRepository.obtenerPacientePorNombre(nombre);
	}
	
	public List<Paciente> obtenerListaPacientePorNombreSinCentroAsignadoSinMedico(){
		return pacienteRepository.obtenerListaPacientePorNombreSinCentroAsignadoSinMedico();
	}
	public Paciente obtenerPacientePorUsuarioLogado(Long id) {
		return pacienteRepository.obtenerPacientePorUsuarioLogado(id);
		
	}

}
