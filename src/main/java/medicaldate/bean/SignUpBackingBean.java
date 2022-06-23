package medicaldate.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Medico;
import medicaldate.model.Paciente;
import medicaldate.model.Rol;
import medicaldate.model.Roles;
import medicaldate.model.User;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.PacienteRepository;
import medicaldate.repository.UserRepository;
import medicaldate.services.RolService;
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

	@Setter
	private Roles roles;

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
	}

	public String doSave() {
		String result = "home";
		if (password.equals(passwordRepeat)) {

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
				nuevoRol = rolService.obtenerRolPorNombre(rolesSelected);
				user.setRol(nuevoRol);	
				user.setEnabled(true);
				usersService.saveUser(user);
				if (rolesSelected.equals("MEDICO")) {
					User nuevoUser = new User();
					nuevoUser = userService.obtenerUsuarioPorNombreUsuario(userName);
					Medico nuevoMedico = new Medico();
					nuevoMedico.setUser(nuevoUser);
					nuevoMedico.setNombre(medico.getNombre());
					medicoRepository.save(nuevoMedico);
				}

				if (rolesSelected.equals("PACIENTE")) {
					User nuevoUser = new User();
					nuevoUser = userService.obtenerUsuarioPorNombreUsuario(userName);
					Paciente nuevoPaciente = new Paciente();
					nuevoPaciente.setUser(nuevoUser);
					nuevoPaciente.setNombre(paciente.getNombre());
					pacienteRepository.save(nuevoPaciente);
				}
				loginBackingBean.setCurrentUser(user);
				clear();
				/*
				 * FacesMessage msg=new FacesMessage("User Created!!!");
				 * FacesContext.getCurrentInstance().addMessage(null, msg);
				 */
				return "login";
			} else {
				FacesMessage msg = new FacesMessage("User name already taken.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				result = "register";
			}
		} else {
			FacesMessage msg = new FacesMessage("Passwords do not match!.");
			FacesContext.getCurrentInstance().addMessage("loginForm:inputPassword", msg);
			result = "register";
		}

		return result;
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
