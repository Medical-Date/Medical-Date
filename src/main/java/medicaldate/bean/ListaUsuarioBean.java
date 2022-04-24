package medicaldate.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	@Getter
	@Setter
	private User usuario;
	
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
	
	@Getter
	@Setter
	private List<String> listaRolesPorNombre;

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

	public void onEditar(Long idUsuario) {
		JsfUtils.setFlashAttribute("idUsuario", idUsuario);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/editarUsuario.xhtml");
	}
	
	public void onEliminar(Long idUsuario) {
		JsfUtils.setFlashAttribute("idUsuario", idUsuario);
	}

	public void editarUser() {
		if (usuario != null) {
			usuario = userRepository.save(usuario);
			
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
			.handleNavigation(FacesContext.getCurrentInstance(), null, "/listUsers.xhtml");


		}
	}	

	public void eliminarUser(User usuarioSeleccionado) {
		if (usuarioSeleccionado != null) {
			userRepository.delete(usuarioSeleccionado);

			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
					.handleNavigation(FacesContext.getCurrentInstance(), null, "/listUsers.xhtml");

			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Usuario borrado correctamente"));

			// PrimeFaces.current().ajax().update("formListadoUsers:usuariosTable");

		}
	}
	
}
