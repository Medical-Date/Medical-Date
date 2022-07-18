package medicaldate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "ROLUSUARIOS")
@Getter
@Setter
public class RolUsuarios {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	
	@Column(name = "Rol", unique = true)
	@NotNull
	private String rol;
	
	@Column(name = "Username")
	@NotNull
	String username;

}
