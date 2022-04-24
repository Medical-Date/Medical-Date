package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Cita;
import medicaldate.model.Medico;
import medicaldate.model.Paciente;
import medicaldate.repository.CitaRepository;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.PacienteRepository;
import medicaldate.services.MedicoService;
import medicaldate.services.PacienteService;

@Component
@ViewScoped
public class CitasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private Cita cita;
	@Getter
	@Setter
	private String medicoSelected;
	@Getter
	@Setter
	private String pacienteSelected;
	@Getter
	@Setter
	private List<Medico> listaMedicos;
	@Getter
	@Setter
	private List<Paciente> listaPacientes;

	@Getter
	@Setter
	private Date fechaCita;

	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private CitaRepository citaRepository;

	@PostConstruct
	public void init() {
		cita= new Cita();
		medicoSelected= "";
		pacienteSelected= "";
		listaMedicos = new ArrayList<>();
		listaPacientes = new ArrayList<>();

		listaMedicos = medicoService.getListaMedicosPorNombre();
		listaPacientes = pacienteService.getListaPacientesPorNombre();

	}

	public void guardarCita() {
		

		if (cita != null) {
			Paciente pacienteSeleccionado = pacienteService.getPacientesPorNombre(pacienteSelected);
			Medico medicoSeleccionado = medicoService.getMedicosPorNombre(medicoSelected);

			cita.setMedico(medicoSeleccionado);
			cita.setPaciente(pacienteSeleccionado);
			cita.setFechaCita(fechaCita);
			citaRepository.save(cita);
			
			medicoSeleccionado.setCita(cita);
			medicoRepository.save(medicoSeleccionado);
				
			pacienteSeleccionado.setCita(cita);
			pacienteRepository.save(pacienteSeleccionado);
			
			
		}

	}

}
