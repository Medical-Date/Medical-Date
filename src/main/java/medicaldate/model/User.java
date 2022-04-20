package medicaldate.model;





import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "USER")
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@NotNull
	private Long id;

	@Column(name = "USERNAME", unique = true)
	@NotNull
	private String userName;

	@Column(name = "FIRSTNAME")
	@NotNull
	private String firstName;

	@Column(name = "LASTNAME")
	@NotNull
	private String lastName;

	@Column(name = "FECHANACIMIENTO")
	private Date fechaNacimiento;

	@Column(name = "DNI")
	@NotNull
	private String dni;

	@Column(name = "DIRECCION")
	@NotNull
	private String direccion;

	@Column(name = "TELEFONO")
	@NotNull
	private String telefono;

	@Column(name = "EMAIL")
	@NotNull
	private String email;

	@Column(name = "PASSWORD")
	@NotNull
	private String password;

	@Column(name = "DBPASSWORD")
	private String dbPassword;

	@Column(name = "DBNAME")
	private String dbName;

//	@Column(name = "ROLES")
//	@Enumerated(EnumType.STRING)
//	private Roles roles;
	
	@OneToOne
	@JoinColumn(name="rol_id")
	private Rol rol;
}
