package medicaldate.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
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

@Component
@ViewScoped
public class Registro implements Serializable {

	private static final long serialVersionUID = 1L;

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
						java.sql.Date fechaNacimientoSql = new java.sql.Date(fechaNacimiento.getTime());
						ps.setDate(9, fechaNacimientoSql);
						Rol nuevoRol = new Rol();
						nuevoRol = rolService.obtenerRolPorNombre(rolesSelected);
						ps.setLong(10, nuevoRol.getId());

						i = ps.executeUpdate();

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
