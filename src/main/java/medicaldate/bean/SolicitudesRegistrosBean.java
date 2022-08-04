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
import medicaldate.model.Rol;
import medicaldate.model.SolicitudesRegistros;
import medicaldate.model.User;
import medicaldate.repository.SolicitudesRegistrosRepository;
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


	@PostConstruct
	public void init() {
		solicitudesRegistros= new SolicitudesRegistros();
		listaRoles = new ArrayList<>();
		listaRoles = rolService.getRoles();
		rolesSelected = "";
		listaRolesPorNombre = new ArrayList<>();
		for (Rol r : listaRoles) {
			listaRolesPorNombre.add(r.getRol());
		}
		listaRolesPorNombre.remove("USUARIO");
		
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
		res="solicitudRegistro.xhtml";
		FacesContext.getCurrentInstance().addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_INFO, "", "Su solicitud se ha enviado correctamente"));
		return res;
	}
	
	

}
