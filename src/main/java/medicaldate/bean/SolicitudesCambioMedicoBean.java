package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Centros;
import medicaldate.model.Medico;
import medicaldate.model.MedicosCentro;
import medicaldate.model.MedicosCentroPaciente;
import medicaldate.model.Paciente;
import medicaldate.model.Rol;
import medicaldate.model.SolicitudesCambioMedico;
import medicaldate.model.SolicitudesRegistros;
import medicaldate.model.User;
import medicaldate.repository.SolicitudesCambioMedicoRepository;
import medicaldate.services.MedicoService;
import medicaldate.services.MedicosCentroPacienteService;
import medicaldate.services.MedicosCentroService;
import medicaldate.services.PacienteService;
import medicaldate.services.UserService;

@Component
@SessionScoped
public class SolicitudesCambioMedicoBean implements Serializable {

	@Getter
	@Setter
	private Paciente pacienteLogado;

	@Getter
	@Setter
	private User userLogado;
	@Autowired
	private UserService userService;
	@Autowired
	private PacienteService pacienteService;
	@Getter
	@Setter
	private Medico medicoAnterior;
	@Autowired
	private MedicosCentroPacienteService medicosCentroPacienteService;
	@Getter
	@Setter
	private MedicosCentroPaciente medicoCentroPaciente;
	@Getter
	@Setter
	private Centros centroPaciente;
	@Autowired
	private MedicosCentroService medicosCentroService;
	@Getter
	@Setter
	private List<MedicosCentro> listaMedicosCentro;
	@Getter
	@Setter
	private List<Medico> listaMedicosEnElCentroDelPaciente;
	@Getter
	@Setter
	private String medicoSelected;
	@Getter
	@Setter
	private SolicitudesCambioMedico solicitudCambioMedico;
	@Autowired
	private SolicitudesCambioMedicoRepository solicitudesCambioMedicoRepository;
	@Autowired
	private MedicoService medicosService;
	
	@Getter
	@Setter
	private List<String> listaNombreMedicos;
	
	

	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		listaMedicosEnElCentroDelPaciente = new ArrayList();
		listaMedicosCentro = new ArrayList();
		listaNombreMedicos = new ArrayList();
		solicitudCambioMedico= new SolicitudesCambioMedico();
		userLogado = userService.getCurrentUser();
		pacienteLogado = pacienteService.obtenerPacientePorUsuarioLogado(userLogado.getId());

		if (pacienteLogado != null) {
			medicoCentroPaciente = medicosCentroPacienteService.obtenerMedicoCentroPacientePorPaciente(pacienteLogado.getId());
			medicoAnterior= medicoCentroPaciente.getIdMedico();
			centroPaciente = medicoCentroPaciente.getIdCentro();
			listaMedicosCentro = medicosCentroService.obtenerMedicoPorCentro(centroPaciente.getId());

			if (!listaMedicosCentro.isEmpty()) {

				for (MedicosCentro m : listaMedicosCentro) {
					listaNombreMedicos.add(m.getIdMedico().getNombre());
				}
				listaNombreMedicos.remove(medicoAnterior.getNombre());
			}
		}

	}
	
	public String enviarSolicitud() {
		String res="";
		Medico medicoNuevo= new Medico();
		medicoNuevo= medicosService.getMedicosPorNombre(medicoSelected);
		solicitudCambioMedico.setMedico(medicoNuevo);
		solicitudCambioMedico.setPaciente(pacienteLogado);
		solicitudesCambioMedicoRepository.save(solicitudCambioMedico);
		res="solicitudEspera.xhtml";
		FacesContext.getCurrentInstance().addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_INFO, "", "Su solicitud se ha enviado correctamente"));
		return res;
	}
	
	
	
	
	

}
