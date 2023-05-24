package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Medico;
import medicaldate.model.Paciente;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.PacienteRepository;
import medicaldate.services.MedicoService;
import medicaldate.services.PacienteService;

@Component
@ViewScoped
public class MedicoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private List<Medico> listaMedicos;
	
	@Getter
	@Setter
	private String nombre;

	
	@Getter
	@Setter
	private Medico medico;
	@Getter
	@Setter
	private Paciente paciente;	

	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Getter
	@Setter
	private String medicoSelected;
	@Getter
	@Setter
	private String pacienteSelected;
	@Getter
	@Setter
	private List<Paciente> listaPacientesPorNombre;
	@Getter
	@Setter
	private List<Medico> listaMedicosPorNombre;
	@Autowired
	private PacienteService pacienteService;
	
	@PostConstruct
	public void init() {
		
		medico= new Medico();
		paciente= new Paciente();
		
		medicoSelected= "";
		pacienteSelected= "";
		listaMedicos = new ArrayList<>();
		listaPacientesPorNombre = new ArrayList<>();

		listaMedicosPorNombre = medicoService.getListaMedicosPorNombre();
		listaPacientesPorNombre = pacienteService.getListaPacientesPorNombre();

		
		
		
		listaMedicos= new ArrayList<>();
		
		listaMedicos= medicoService.getMedicos();
		
	}
	
	
	public void guardarMedico() {
		if (medico != null) {
			medico = medicoRepository.save(medico);
			
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
			.handleNavigation(FacesContext.getCurrentInstance(), null, "/listMedicos.xhtml");


		}
	}
	


}
