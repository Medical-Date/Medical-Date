package medicaldate.bean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Medico;
import medicaldate.model.Paciente;
import medicaldate.model.Rol;
import medicaldate.model.RolUsuarios;
import medicaldate.model.User;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.PacienteRepository;
import medicaldate.repository.RolUsuariosRepository;
import medicaldate.repository.UserRepository;
import medicaldate.services.RolService;
import medicaldate.services.RolUsuariosService;
import medicaldate.services.UserService;
import medicaldate.util.JsfUtils;

@Controller("SignUpController")
@SessionScope
public class SignUpBackingBean {


	@Getter
	@Setter
	String passwordRepeat;

	@Autowired
	@Getter
	@Setter
	LoginBackingBean loginBackingBean;

	@Autowired
	UserService usersService;

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

	@Autowired
	private RolService rolService;
	
	@Autowired
	private RolUsuariosRepository rolUsuariosRepository;

	@Getter
	@Setter
	private List<String> listaRolesPorNombre;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private PacienteRepository pacienteRepository;

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

	@PostConstruct
	public void init() {

		medico = new Medico();
		paciente = new Paciente();
		listaRoles = new ArrayList<>();
		listaRoles = rolService.getRoles();
		listaRolesPorNombre = new ArrayList<>();
		rolesSelected = "";
		for (Rol r : listaRoles) {
			listaRolesPorNombre.add(r.getRol());
		}
		listaRolesPorNombre.remove("USUARIO");
	}

	public String doSave() {
		String result = "home";
		Boolean validacionesGuardar=validacionesGuardar();		
			
			if(validacionesGuardar) {
				
			

			if (!usersService.existsUser(userName)) {
				User user = new User();
				user.setUserName(userName);
				user.setPassword(password);
				user.setEmail(email);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setDni(dni);
				user.setDireccion(direccion);
				user.setTelefono(telefono);
				user.setFechaNacimiento(fechaNacimiento);
				Rol nuevoRol = new Rol();
				nuevoRol = rolService.obtenerRolPorNombre("USUARIO");
				user.setRol(nuevoRol);	
				user.setEnabled(true);
				usersService.saveUser(user);				
				loginBackingBean.setCurrentUser(user);
				clear();

				return "solicitudRegistro.xhtml";
		} else {
			
			result = "register.xhtml";
		}
		
		}else {
			result = "register.xhtml";
		}

	
		return result;
	}
	
	private Boolean validacionesGuardar() {
		Boolean esValido=true;
		String camposValidos="";
		
		if((userName.isBlank() || userName == null) || 
			(password.isBlank() || password == null) ||
			(passwordRepeat.isBlank() || passwordRepeat == null) ||
			(dni.isBlank() || dni == null) ||
			(firstName.isBlank() || firstName == null) ||
			(lastName.isBlank() || lastName == null) ||
			(fechaNacimiento == null) ||
			(direccion.isBlank() || direccion == null) ||
			(telefono.isBlank() || telefono == null) ||
			(email.isBlank() || email == null) ) {
			camposValidos="Debe rellenar todos los campos obligatorios";

			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", camposValidos));
			esValido=false;
		}
		
		LocalDate fechaActual= LocalDate.now();
		if(fechaNacimiento!=null) {		
		if(fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(fechaActual) || fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(fechaActual))  {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "La fecha de nacimiento debe ser anterior a la actual"));
			esValido=false;
		}
			
		}
		if(!dni.isBlank() &&  !JsfUtils.esNIEValido(dni) ) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El DNI no es válido"));
			esValido=false;
		}
		if(!telefono.isBlank() && !(telefono.startsWith("6")) && !(telefono.startsWith("7")) && !(telefono.startsWith("9")) && !(telefono.startsWith("8"))) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El número de teléfono no es válido"));
			esValido=false;
		}
		if(!telefono.isBlank() && telefono.length()!=9) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El número de teléfono debe tener 9 dígitos"));
			esValido=false;
		}
		
		if(!email.isBlank() &&  !email.contains("@")) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Formato de email no válido"));
			esValido=false;
		}
		if(!userName.isBlank() &&  userName.length()<3) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre de usuario debe ser mayor de 2 caracteres"));
			esValido=false;
		}
		if(!firstName.isBlank() &&  firstName.length()<3) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre debe ser mayor de 2 caracteres"));
			esValido=false;
		}
		if(!lastName.isBlank() &&  lastName.length()<3) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El apellido debe ser mayor de 2 caracteres"));
			esValido=false;
		}
		if(!password.equals(passwordRepeat)) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "La contraseña no coincide"));
			esValido=false;
		}
		if(!password.isBlank() && password.length()<8) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "La contraseña debe tener más de 7 caracteres"));
			esValido=false;
		}


		
		return esValido;
	}
	
	

	public void comprobarSiEsMedicoOPaciente() {
		esMedico=false;
		esPaciente=false;
		if(rolesSelected!=null &&  rolesSelected.equals("MEDICO")) {
			esMedico=true;


		}else if(rolesSelected!=null &&  rolesSelected.equals("PACIENTE")) {
			esPaciente=true;
		}
	}

	public void clear() {
		userName = "";
		email = "";
		password = "";
		firstName = "";
		lastName = "";
		dni = "";
		direccion = "";
		telefono = "";
		fechaNacimiento = new Date();		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordRepeat == null) ? 0 : passwordRepeat.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((loginBackingBean == null) ? 0 : loginBackingBean.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SignUpBackingBean other = (SignUpBackingBean) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (passwordRepeat == null) {
			if (other.passwordRepeat != null)
				return false;
		} else if (!passwordRepeat.equals(other.passwordRepeat))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (loginBackingBean == null) {
			if (other.loginBackingBean != null)
				return false;
		} else if (!loginBackingBean.equals(other.loginBackingBean))
			return false;
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -502758519454403593L;
}
