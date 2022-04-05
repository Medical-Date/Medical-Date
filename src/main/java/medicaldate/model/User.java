package medicaldate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

//import org.springframework.format.annotation.DateTimeFormat;

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
	
	/*@Column(name = "FECHANACIMIENTO")
	private Date fechaNacimiento;*/

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

}
