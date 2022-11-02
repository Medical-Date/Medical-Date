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
import medicaldate.model.Medico;
import medicaldate.model.Paciente;
import medicaldate.model.Rol;
import medicaldate.model.SolicitudesRegistros;
import medicaldate.model.User;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.PacienteRepository;
import medicaldate.repository.SolicitudesRegistrosRepository;
import medicaldate.repository.UserRepository;
import medicaldate.services.RolService;
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
	


	@PostConstruct
	public void init() {
		solicitudesRegistros= new SolicitudesRegistros();
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
		}
		if(usuarioSolicitante.getRol().getRol().equals("MEDICO")) {
			Medico nuevoMedico= new Medico();
			nuevoMedico.setNombre(usuarioSolicitante.getFirstName());
			nuevoMedico.setUser(usuarioSolicitante);
			nuevoMedico.setEsAsignado(false);
			medicoRepository.save(nuevoMedico);
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
	
	

}
