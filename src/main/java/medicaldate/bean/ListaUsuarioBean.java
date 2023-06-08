package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Historial;
import medicaldate.model.Medico;
import medicaldate.model.Paciente;
import medicaldate.model.Rol;
import medicaldate.model.SolicitudesRegistros;
import medicaldate.model.User;
import medicaldate.repository.HistorialRepository;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.MedicosCentroPacienteRepository;
import medicaldate.repository.PacienteRepository;
import medicaldate.repository.SolicitudesRegistrosRepository;
import medicaldate.repository.UserRepository;
import medicaldate.services.HistorialService;
import medicaldate.services.MedicoService;
import medicaldate.services.MedicosCentroPacienteService;
import medicaldate.services.PacienteService;
import medicaldate.services.RolService;
import medicaldate.services.SolicitudesRegistrosService;
import medicaldate.services.UserService;
import medicaldate.util.JsfUtils;

@Component
@ViewScoped
public class ListaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String firstName;

	@Getter
	@Setter
	private String lastName;

	@Getter
	@Setter
	private Date fechaNacimiento;
	
	@Getter
	@Setter
	private Date fechaNacimientoSql;

	@Getter
	@Setter
	private String dni;

	@Getter
	@Setter
	private String direccion;

	@Getter
	@Setter
	private String telefono;

	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String dbName;

	@Getter
	@Setter
	private String dbPassword;

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String userName;

	@Getter
	@Setter
	private User usuarioConNombre;

	@Getter
	@Setter
	private List<User> listaUsuarios;

	@Autowired
	private UserService userService;
	@Autowired
	private RolService rolService;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private MedicosCentroPacienteRepository medicoCentroPacienteRepository;
	
	@Autowired
	private MedicosCentroPacienteService medicoCentroPacienteService;
	
	@Autowired
	private HistorialRepository historialRepository;
	
	@Autowired
	private SolicitudesRegistrosRepository solicitudesRegistrosRepository;
	
	@Autowired
	private HistorialService historialService;
	
	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private SolicitudesRegistrosService solicitudesRegistrosService;

	@Getter
	@Setter
	private User usuario;
	
	@Getter
	@Setter
	private Medico medico;
	
	@Getter
	@Setter
	private Paciente paciente;
	
	@Getter
	@Setter
	private Boolean esMedico;
	@Getter
	@Setter
	private Boolean esPaciente;
	@Getter
	@Setter
	private String rolesSelected;
	
	@Getter
	@Setter
	private List<Rol> listaRoles;
	
	@Getter
	@Setter
	private List<String> listaRolesPorNombre;
	
	@Autowired
	private PacienteService pacienteService;

	@PostConstruct
	public void init() {
		Long idUsuario = (Long) JsfUtils.getFlashAttribute("idUsuario");
		usuario = new User();
		listaUsuarios = new ArrayList<>();
		listaUsuarios = userService.getUsers();	
		if (idUsuario != null) {
			usuario = userService.getUserById(idUsuario);
		}
	}
		
	
	public void inicio() {
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/home.xhtml");
	}
	public void listaUsuarios() {
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/listUsers.xhtml");
	}


	
	public void onEliminar(Long idUsuario) {
		JsfUtils.setFlashAttribute("idUsuario", idUsuario);
	}

	public void confirmarEliminarUser() {
		System.out.println("hola");
	}
	public void eliminarUser(User usuarioSeleccionado) {
		if (usuarioSeleccionado != null) {
			eliminarUserCompleto(usuarioSeleccionado);
			userRepository.delete(usuarioSeleccionado);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Usuario borrado correctamente"));
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
					.handleNavigation(FacesContext.getCurrentInstance(), null, "/listUsers.xhtml");

		}
	}
	
	public void eliminarUserCompleto(User usuarioSeleccionado) {
		Long idUsuario= usuarioSeleccionado.getId();
		Historial historial= historialService.obtenerHistorialPorUsuario(idUsuario);
		Medico medico= medicoService.obtenerMedicoPorUsuario(idUsuario);
		Paciente paciente= pacienteService.obtenerPacientePorUsuarioLogado(idUsuario);
		SolicitudesRegistros solRegistro= solicitudesRegistrosService.comprobarSiExisteSolicitudPorUsuario(idUsuario);
		if(solRegistro!=null) {
			solicitudesRegistrosRepository.delete(solRegistro);
		}
		if(historial!=null) {
			historialRepository.delete(historial);
		}
		if(medico!=null) {
			

		}
		if(paciente!=null) {
			pacienteRepository.delete(paciente);
		}		
		
	}
	
}
