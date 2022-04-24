package medicaldate.model;

import java.util.Date;

//import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

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
	@NotNull
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USERNAME")
	@NotNull
	private String userName;

	@Column(name = "FIRSTNAME")
	@NotNull
	private String firstName;

	@Column(name = "LASTNAME")
	@NotNull
	private String lastName;

	@Column(name = "FECHANACIMIENTO")
	@NotNull
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

	@Column(name = "DBNAME")
	private String dbName;

	@Column(name = "DBPASSWORD")
	private String dbPassword;

	@Column(name = "ROLES")
	@Enumerated(EnumType.STRING)
	private Roles roles;

}
