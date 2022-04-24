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
	private List<Paciente> listaPacientes;
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
		listaPacientes = new ArrayList<>();

		listaMedicosPorNombre = medicoService.getListaMedicosPorNombre();
		listaPacientes = pacienteService.getListaPacientesPorNombre();

		
		
		
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
	
	public void asignarMedico() {
		if(medico != null && paciente!=null) {
			Set<Paciente> conjuntoDePacientes= new HashSet<>();
			Paciente pacienteSeleccionado = pacienteService.getPacientesPorNombre(pacienteSelected);
			Medico medicoSeleccionado = medicoService.getMedicosPorNombre(medicoSelected);
			paciente= pacienteSeleccionado;
			medico=medicoSeleccionado;
			paciente.setMedico(medicoSeleccionado);
			conjuntoDePacientes.add(paciente);
			medico.setPaciente(conjuntoDePacientes);
			
			
			pacienteRepository.save(paciente);		
			medicoRepository.save(medico);
		}
		
	}


}
