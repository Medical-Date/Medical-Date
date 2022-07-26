package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.MedicosCentroPaciente;
import medicaldate.repository.MedicosCentroPacienteRepository;
import medicaldate.services.MedicosCentroPacienteService;

@Component
@ViewScoped
public class ListaMedicosPacienteCentroBean  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MedicosCentroPacienteService medicosCentroPacienteService;
	
	@Autowired
	private MedicosCentroPacienteRepository medicosCentroPacienteRepository;
	
	@Getter @Setter
	private List<MedicosCentroPaciente> listaMedicosCentroPaciente;
	
	@PostConstruct
	public void init() {
		
		listaMedicosCentroPaciente= new ArrayList<>();
		listaMedicosCentroPaciente = medicosCentroPacienteService.getAll();
		
	}

}
