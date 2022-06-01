package medicaldate.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Medico;
import medicaldate.model.Rol;
import medicaldate.model.User;
import medicaldate.services.UserService;

@Component
@SessionScoped
@ManagedBean(value = "user")
public class SesionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private User user;
	@Getter
	@Setter
	private Rol rol;
	
	@Getter
	@Setter
	private String nombre;

	@Getter
	@Setter
	private Boolean esPaciente;

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		
		nombre= nombre();

	}

	public String nombre() {
		String n = "";
		user.getUserName();
		return n;

	}

	public Boolean comprobarSiEsPaciente() {

		esPaciente = false;

		if (user.getRol().getRol().equals("PACIENTE")) {
			esPaciente = true;
		}
		return esPaciente;

	}

}
