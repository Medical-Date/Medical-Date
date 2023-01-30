package medicaldate.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import medicaldate.model.Cita;
import medicaldate.model.Medico;
import medicaldate.model.MedicosCentro;
import medicaldate.model.MedicosCentroPaciente;
import medicaldate.model.Paciente;
import medicaldate.model.Rol;
import medicaldate.model.RolUsuarios;
import medicaldate.model.SolicitudesCambioCentro;
import medicaldate.model.SolicitudesCambioMedico;
import medicaldate.model.SolicitudesRegistros;
import medicaldate.model.User;
import medicaldate.repository.CitaRepository;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.MedicosCentroPacienteRepository;
import medicaldate.repository.PacienteRepository;
import medicaldate.repository.RolUsuariosRepository;
import medicaldate.repository.SolicitudesCambioCentroRepository;
import medicaldate.repository.SolicitudesCambioMedicoRepository;
import medicaldate.repository.SolicitudesRegistrosRepository;
import medicaldate.repository.UserRepository;
import medicaldate.services.CitaService;
import medicaldate.services.MedicosCentroPacienteService;
import medicaldate.services.MedicosCentroService;
import medicaldate.services.RolService;
import medicaldate.services.SolicitudesCambioCentroService;
import medicaldate.services.SolicitudesCambioMedicoService;
import medicaldate.services.SolicitudesRegistrosService;
import medicaldate.services.UserService;

@Component
@SessionScoped
public class SolicitudesRegistrosBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	private SolicitudesRegistros solicitudesRegistros;
	
	@Autowired
	private SolicitudesRegistrosRepository solicitudesRegistrosRepository;
	@Autowired
	private SolicitudesRegistrosService solicitudesRegistrosService;
	@Autowired
	private UserRepository userRepository;
	@Getter
	@Setter
	private List<String> listaRolesPorNombre;
	
	@Getter
	@Setter
	private String rolesSelected;
	@Getter
	@Setter
	private List<Rol> listaRoles;
	
	@Autowired
	private RolService rolService;
	@Autowired
	UserService userService;
	@Getter
	@Setter
	private List<SolicitudesRegistros> listaSolicitudesRegistros;
	@Getter
	@Setter
	private List<SolicitudesRegistros> listaSolicitudesRegistrosRechazadas;
	@Getter
	@Setter
	private List<SolicitudesRegistros> listaSolicitudesRegistrosAceptadas;
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Getter
	@Setter
	private List<SolicitudesCambioMedico> listaSolicitudesCambioMedico;
	@Getter
	@Setter
	private List<SolicitudesCambioMedico> listaSolicitudesCambioMedicoRechazadas;
	@Getter
	@Setter
	private List<SolicitudesCambioMedico> listaSolicitudesCambioMedicoAceptadas;
	@Getter @Setter
	private SolicitudesCambioMedico solicitudesCambioMedico;
	
	@Autowired
	private SolicitudesCambioMedicoRepository solicitudesCambioMedicoRepository;
	@Autowired
	private SolicitudesCambioMedicoService solicitudesCambioMedicoService;
	@Getter
	@Setter
	private Medico medicoAnterior;
	@Autowired
	private MedicosCentroPacienteService medicosCentroPacienteService;
	@Getter
	@Setter
	private MedicosCentroPaciente medicoCentroPaciente;
	@Autowired
	private CitaService citaService;
	@Autowired
	private CitaRepository citaRepository;
	@Autowired
	private MedicosCentroPacienteRepository medicosCentroPacienteRepository;
	@Autowired
	private RolUsuariosRepository rolUsuariosRepository;
	

	


	@PostConstruct
	public void init() {
		solicitudesRegistros= new SolicitudesRegistros();
		solicitudesCambioMedico= new SolicitudesCambioMedico();
		listaSolicitudesRegistros= new ArrayList<>();
		listaSolicitudesRegistrosRechazadas= new ArrayList<>();
		listaSolicitudesRegistrosAceptadas=new ArrayList<>();
		listaRoles = new ArrayList<>();
		listaRoles = rolService.getRoles();
		rolesSelected = "";
		listaRolesPorNombre = new ArrayList<>();
		for (Rol r : listaRoles) {
			listaRolesPorNombre.add(r.getRol());
		}
		listaRolesPorNombre.remove("USUARIO");
		listaRolesPorNombre.remove("ADMINISTRADOR");
		listaSolicitudesRegistros= solicitudesRegistrosService.listaSolicitudesPendientes();
		listaSolicitudesRegistrosRechazadas=solicitudesRegistrosService.listaSolicitudesRechazadas();
		listaSolicitudesRegistrosAceptadas=solicitudesRegistrosService.listaSolicitudesAceptadas();
		
		cargarSolicitudesCambioMedico();
		
		
	}
	
	public void cargarSolicitudesCambioMedico() {
		listaSolicitudesCambioMedico= new ArrayList<>();
		listaSolicitudesCambioMedicoRechazadas= new ArrayList<>();
		listaSolicitudesCambioMedicoAceptadas= new ArrayList<>();
		solicitudesCambioMedico= new SolicitudesCambioMedico();
		listaSolicitudesCambioMedico=solicitudesCambioMedicoService.listaSolicitudesCambioMedicoPendientes();
		listaSolicitudesCambioMedicoRechazadas=solicitudesCambioMedicoService.listaSolicitudesCambioMedicoRechazadas();
		listaSolicitudesCambioMedicoAceptadas=solicitudesCambioMedicoService.listaSolicitudesCambioMedicoAceptadas();
	}
	
	public String enviarSolicitud() {
		String res="";
		User user = userService.getCurrentUser();
		SolicitudesRegistros s= new SolicitudesRegistros();
		Rol nuevoRol= new Rol();
		nuevoRol= rolService.obtenerRolPorNombre(rolesSelected);
		s.setUser(user);
		s.setRol(nuevoRol);
		solicitudesRegistrosRepository.save(s);
		res="solicitudEspera.xhtml";
		FacesContext.getCurrentInstance().addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_INFO, "", "Su solicitud se ha enviado correctamente"));
		return res;
	}
	
	public void aceptarSolicitud(SolicitudesRegistros solReg) {
		RolUsuarios rolUsuarios=new RolUsuarios();
		User usuarioSolicitante=new User();
		usuarioSolicitante= solReg.getUser();
		solReg.setEstado(true);
		solicitudesRegistrosRepository.save(solReg);
		usuarioSolicitante.setRol(solReg.getRol());
		userRepository.save(usuarioSolicitante);
		if(usuarioSolicitante.getRol().getRol().equals("PACIENTE")) {
			Paciente nuevoPaciente= new Paciente();
			nuevoPaciente.setNombre(usuarioSolicitante.getFirstName());
			nuevoPaciente.setUser(usuarioSolicitante);
			nuevoPaciente.setTieneCentro(false);
			nuevoPaciente.setTieneMedico(false);
			pacienteRepository.save(nuevoPaciente);
			rolUsuarios.setRol("PACIENTE");
			rolUsuarios.setUsername(usuarioSolicitante.getUserName());
			rolUsuariosRepository.save(rolUsuarios);
			
		}
		if(usuarioSolicitante.getRol().getRol().equals("MEDICO")) {
			Medico nuevoMedico= new Medico();
			nuevoMedico.setNombre(usuarioSolicitante.getFirstName());
			nuevoMedico.setUser(usuarioSolicitante);
			nuevoMedico.setEsAsignado(false);
			medicoRepository.save(nuevoMedico);
			rolUsuarios.setRol("MEDICO");
			rolUsuarios.setUsername(usuarioSolicitante.getUserName());
			rolUsuariosRepository.save(rolUsuarios);
		}
		FacesContext.getCurrentInstance().addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_INFO, "", "La solicitud se ha aceptado correctamente"));
		listaSolicitudesRegistros.remove(solReg);
		listaSolicitudesRegistrosAceptadas.add(solReg);
	}
	public void rechazarSolicitud(SolicitudesRegistros solReg) {
		solReg.setEstado(false);
		solicitudesRegistrosRepository.save(solReg);
		listaSolicitudesRegistrosRechazadas.add(solReg);
		listaSolicitudesRegistros.remove(solReg);
		FacesContext.getCurrentInstance().addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_INFO, "", "La solicitud se ha rechazado correctamente"));
	}
	
	public Boolean estaAceptada(SolicitudesRegistros solReg) {
		Boolean res = true;
		if(solReg.getEstado()!=null &&solReg.getEstado().equals(true)) {
			res=false;
		}
		return res;
		
	}
	
	public String estadoSolicitud(SolicitudesRegistros solReg) {
		String res="";
		if(solReg.getEstado()!=null &&    solReg.getEstado().equals(false)) {
			res="RECHAZADA";
		}else if(solReg.getEstado()!=null && solReg.getEstado().equals(true)) {
			res="ACEPTADA";
		}else {
			res="PENDIENTE";
		}
		return res;
	}
	
	public String estadoSolicitudCambioMedico(SolicitudesCambioMedico solCambiReg) {
		String res="";
		if(solCambiReg.getEstado()!=null &&    solCambiReg.getEstado().equals(false)) {
			res="RECHAZADA";
		}else if(solCambiReg.getEstado()!=null && solCambiReg.getEstado().equals(true)) {
			res="ACEPTADA";
		}else {
			res="PENDIENTE";
		}
		return res;
	}
	
	
	public String obtenerMedicoNuevoPorPaciente(Paciente idPaciente) {
		String res="";
		medicoCentroPaciente = medicosCentroPacienteService.obtenerMedicoCentroPacientePorPaciente(idPaciente.getId());
		medicoAnterior= medicoCentroPaciente.getIdMedico();
		res=medicoAnterior.getUser().getFirstName() +" "+ medicoAnterior.getUser().getLastName();
		return res;
	}
	
	public void aceptarSolicitudCambioMedico(SolicitudesCambioMedico solCambiReg) {
		List<Cita> citasPenditesPaciente= new ArrayList<>();
		
		
		solCambiReg.setEstado(true);
		solicitudesCambioMedicoRepository.save(solCambiReg);
		medicoCentroPaciente = medicosCentroPacienteService.obtenerMedicoCentroPacientePorPaciente(solCambiReg.getPaciente().getId());
		medicoCentroPaciente.setIdMedico(solCambiReg.getMedico());
		medicosCentroPacienteRepository.save(medicoCentroPaciente);
		
		citasPenditesPaciente= citaService.getListaCitasByPaciente(solCambiReg.getPaciente().getId());
		LocalDate fechaActual= LocalDate.now();
		for(Cita c: citasPenditesPaciente) {
			if(c.getDiaCita().isAfter(fechaActual)) {
				c.setDisponible(true);
				c.setPaciente(null);
				citaRepository.save(c);
			}
		}	
		FacesContext.getCurrentInstance().addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_INFO, "", "La solicitud se ha aceptado correctamente"));
		listaSolicitudesCambioMedico.remove(solCambiReg);
		listaSolicitudesCambioMedicoAceptadas.add(solCambiReg);
	}
	
	public void rechazarSolicitudCambioMedico(SolicitudesCambioMedico solCambiReg) {
		solCambiReg.setEstado(false);
		solicitudesCambioMedicoRepository.save(solCambiReg);
		listaSolicitudesCambioMedicoRechazadas.add(solCambiReg);
		listaSolicitudesCambioMedico.remove(solCambiReg);
		FacesContext.getCurrentInstance().addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_INFO, "", "La solicitud se ha rechazado correctamente"));
	}
	
	
	

}
