package medicaldate.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Enfermedad;
import medicaldate.model.Historial;
import medicaldate.model.Medico;
import medicaldate.model.MedicosCentroPaciente;
import medicaldate.model.Paciente;
import medicaldate.model.TipoSangre;
import medicaldate.model.User;
import medicaldate.repository.HistorialRepository;
import medicaldate.repository.UserRepository;
import medicaldate.services.EnfermedadService;
import medicaldate.services.HistorialService;
import medicaldate.services.MedicoService;
import medicaldate.services.MedicosCentroPacienteService;
import medicaldate.services.PacienteService;
import medicaldate.services.UserService;
import medicaldate.util.JsfUtils;

@Component
@ViewScoped
public class HistorialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String contactoEmergencia;

	@Getter
	@Setter
	private String tlfContactoEmergencia;

	@Getter
	@Setter
	private TipoSangre tipoSangre;

	@Getter
	@Setter
	private User usuario;

	@Getter
	@Setter
	private Historial historial;

	@Getter
	@Setter
	private List<Historial> listaHistorial;

	@Getter
	@Setter
	private List<Enfermedad> listaEnfermedad;

	@Autowired
	private HistorialRepository historialRepository;

	@Autowired
	private HistorialService historialService;

	@Autowired
	private EnfermedadService enfermedadService;

	@Getter
	@Setter
	private List<String> listaPaciente;

	@Autowired
	private UserService userService;

	@Autowired
	private PacienteService pacienteService;
	@Autowired
	private UserRepository userRepository;

	@Getter
	@Setter
	private String pacienteSelected;

	@Getter
	@Setter
	private List<MedicosCentroPaciente> listaPacienteSinHistorial;

	@Autowired
	private MedicosCentroPacienteService medicosCentroPacienteService;
	@Autowired
	private MedicoService medicoService;

	@PostConstruct
	public void init() {
		Long idHistorial = (Long) JsfUtils.getFlashAttribute("idHistorial");
		User user = userService.getCurrentUser();
		Medico medicoLogado = medicoService.obtenerMedicoPorUsuario(user.getId());
		if (medicoLogado != null) {

			historial = new Historial();
			usuario = new User();
			pacienteSelected = "";

			id = historial.getId();
			contactoEmergencia = historial.getContactoEmergencia();
			tlfContactoEmergencia = historial.getTlfContactoEmergencia();
			tipoSangre = historial.getTipoSangre();
			listaHistorial = new ArrayList<Historial>();
			listaHistorial = historialService.getHistoriales();
			listaEnfermedad = new ArrayList<Enfermedad>();
			listaEnfermedad = enfermedadService.getEnfermedades();
			listaPaciente = new ArrayList<>();
			listaPacienteSinHistorial = new ArrayList<>();
			listaPacienteSinHistorial = medicosCentroPacienteService
					.obtenerListPacientePorMedicoSinHistorial(medicoLogado.getId());
			for (MedicosCentroPaciente mcp : listaPacienteSinHistorial) {
				listaPaciente.add(mcp.getIdPaciente().getNombre());
			}

			if (idHistorial != null) {
				historial = historialService.getHistorialById(idHistorial);
			}
		} else {
			listaHistorial = new ArrayList<Historial>();
			listaEnfermedad = new ArrayList<Enfermedad>();
			listaPaciente = new ArrayList<>();
			listaPacienteSinHistorial = new ArrayList<>();
		}
	}

	public void guardarHistorial() {

		historial = new Historial();

		if (historial != null) {
			Paciente pacienteSeleccionado = pacienteService.getPacientesPorNombre(pacienteSelected);

			historial.setContactoEmergencia(contactoEmergencia);
			historial.setTlfContactoEmergencia(tlfContactoEmergencia);
			historial.setTipoSangre(tipoSangre);
			historial.setPaciente(pacienteSeleccionado);
			historial.setUser(pacienteSeleccionado.getUser());
			usuario = userService.getUserById(pacienteSeleccionado.getUser().getId());
			historialRepository.save(historial);
			usuario.setHistorial(historial);
			userRepository.save(usuario);
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
					.handleNavigation(FacesContext.getCurrentInstance(), null, "/listaHistoriales.xhtml");
		}

	}

	public void editarHistorial() {
		if (historial != null) {
			historial = historialRepository.save(historial);
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
					.handleNavigation(FacesContext.getCurrentInstance(), null, "/listaHistoriales.xhtml");
		}
	}

	public void onEditar(Long idHistorial) {
		JsfUtils.setFlashAttribute("idHistorial", idHistorial);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/editarHistorial.xhtml");
	}

}
