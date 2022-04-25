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
import medicaldate.model.Medico;
import medicaldate.model.MedicosPacientes;
import medicaldate.repository.MedicosPacientesRepository;
import medicaldate.services.MedicosPacientesService;

@Component
@ViewScoped
public class MedicosPacientesBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MedicosPacientesService medicosPacientesService;
	
	@Autowired
	private MedicosPacientesRepository medicosPacientesRepository;

	@Getter
	@Setter
	private List<MedicosPacientes> listaMedicosPacientes;
	
	
	@PostConstruct
	public void init() {
		
		listaMedicosPacientes= new ArrayList<>();
		listaMedicosPacientes= medicosPacientesService.getMedicosPacientes();
		
	}

}
