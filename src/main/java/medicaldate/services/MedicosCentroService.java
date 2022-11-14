package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import medicaldate.model.MedicosCentro;
import medicaldate.model.MedicosPacientes;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.MedicosCentroRepository;

@Service
public class MedicosCentroService {
	
	private MedicosCentroRepository medicosCentroRepository;

	public MedicosCentroService(@Autowired MedicosCentroRepository medicosCentroRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.medicosCentroRepository = medicosCentroRepository;

	}
	
	public List<MedicosCentro> getMedicosCentro(){
		return (List<MedicosCentro>) medicosCentroRepository.findAll();
	}
	
	public List<MedicosCentro> obtenerMedicoPorCentro(Long id){
		return medicosCentroRepository.obtenerMedicoPorCentro(id);
	}
	
	public MedicosCentro obtenerMedico(long id) {
		return medicosCentroRepository.obtenerMedico(id);
	}

}
