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
public class UserBean implements Serializable {

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
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/medicaldate?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "r00t-P@$$w0rd");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		medico= new Medico();
		paciente= new Paciente();

		usuario = new User();
		userName = usuario.getUserName();
		firstName = usuario.getFirstName();
		lastName = usuario.getLastName();
		fechaNacimiento = new Date(); 
		 java.sql.Date fechaNacimientoSql  = new java.sql.Date(fechaNacimiento.getTime());
		//fechaNacimiento = new java.sql.Date(message.getCreatedTime().getTime());
//		fechaNacimientoString= new String();
//		fechaNacimientoString= fechaNacimiento
		 fechaNacimiento= fechaNacimientoSql;
				
		
		dni = usuario.getDni();
		direccion = usuario.getDireccion();
		telefono = usuario.getTelefono();
		password = usuario.getPassword();
		email = usuario.getEmail();
		dbName = usuario.getDbName();
		dbPassword = usuario.getDbPassword();
//		roles = usuario.getRoles();
		id = usuario.getId();
		//fechaNacimiento= usuario.getFechaNacimiento();
		listaUsuarios = new ArrayList<>();
		listaUsuarios = userService.getUsers();
		listaRoles = new ArrayList<>();
		listaRoles = rolService.getRoles();
		listaRolesPorNombre = new ArrayList<>();
		rolesSelected="";
		for(Rol r: listaRoles) {
			listaRolesPorNombre.add(r.getRol());
		}
		

		if (idUsuario != null) {
			usuario = userService.getUserById(idUsuario);
		}
	}
	

	public String add() {
		int i = 0;
		if (firstName != null) {
			PreparedStatement ps = null;
			Connection con = null;
			try {
				con = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/medicaldate?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
						"root", "r00t-P@$$w0rd");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {

				if (con != null) {

					String sql = "INSERT INTO user(username,firstname, password, lastname, email, dni, direccion, telefono,fechaNacimiento,rol_id) VALUES(?,?,?,?,?,?,?,?,?,?)";
					ps = con.prepareStatement(sql);
					if (validarRegistrar()) {
						ps.setString(1, userName);
						ps.setString(2, firstName);
						ps.setString(3, password);
						ps.setString(4, lastName);
						ps.setString(5, email);
						ps.setString(6, dni);
						ps.setString(7, direccion);
						ps.setString(8, telefono);
						java.sql.Date fechaNacimientoSql  = new java.sql.Date(fechaNacimiento.getTime());
						ps.setDate(9, fechaNacimientoSql);
						Rol nuevoRol= new Rol();
						nuevoRol= rolService.obtenerRolPorNombre(rolesSelected);
						ps.setLong(10, nuevoRol.getId());
					
						i = ps.executeUpdate();
						
						if(rolesSelected.equals("MEDICO")) {
							User nuevoUser= new User();
							nuevoUser=userService.obtenerUsuarioPorNombreUsuario(userName);
							Medico nuevoMedico= new Medico();
							nuevoMedico.setUser(nuevoUser);
							nuevoMedico.setNombre(medico.getNombre());
							medicoRepository.save(nuevoMedico);
						}
						
						if(rolesSelected.equals("PACIENTE")) {
							User nuevoUser= new User();
							nuevoUser=userService.obtenerUsuarioPorNombreUsuario(userName);
							Paciente nuevoPaciente= new Paciente();
							nuevoPaciente.setUser(nuevoUser);
							nuevoPaciente.setNombre(paciente.getNombre());
							pacienteRepository.save(nuevoPaciente);
						}
						
						System.out.println("Data Added Successfully");
						con.close();
						ps.close();
					}
				}

			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (i > 0) {
			return "success";
		} else
			return "unsuccess";
	}

	public void dbData(String uName) {
		if (uName != null) {
			PreparedStatement ps = null;
			Connection con = null;
			ResultSet rs = null;

			try {
				con = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/medicaldate?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
						"root", "r00t-P@$$w0rd");
				if (con != null) {
					String sql = "select userName,password from user where userName = '" + uName + "'";
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					rs.next();
					dbName = rs.getString("userName");
					dbPassword = rs.getString("password");
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}

		}
	}

	public String login() {
		dbData(userName);
		if (userName.equals(dbName) && password.equals(dbPassword)) {
			return "welcome";
		} else
			return "unsuccess";
	}

	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/home.xhtml");
	}
	
	public void inicio() {
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/home.xhtml");
	}

	private Boolean validarRegistrar() {
		Boolean res = true;
		listaUsuarios = (List<User>) userRepository.findAll();
		if (!listaUsuarios.isEmpty()) {

			for (User u : listaUsuarios)
				if (u.getUserName().equals(userName)) {
					res = false;
					break;
				} else {
					res = true;
				}
		}
		return res;
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
	
	public String onSession() {
		dbData(usuario.getUserName());
		
		return usuario.getUserName();
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
		if (usuario != null) {
			userRepository.delete(usuarioSeleccionado);

			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
					.handleNavigation(FacesContext.getCurrentInstance(), null, "/listUsers.xhtml");

			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Usuario borrado correctamente"));

			// PrimeFaces.current().ajax().update("formListadoUsers:usuariosTable");

		}
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
	

}
