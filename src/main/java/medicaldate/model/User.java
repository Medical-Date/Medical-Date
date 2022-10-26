package medicaldate.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

	@OneToOne
	@JoinColumn(name = "rol_id")
	private Rol rol;
	
	boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;
	
	@OneToOne
	@JoinColumn(name="historial_id")
	private Historial historial;
	
	public User(){
		authorities=new HashSet<Authorities>();
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Authorities> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authorities> authorities) {
		this.authorities = authorities;
	}
}