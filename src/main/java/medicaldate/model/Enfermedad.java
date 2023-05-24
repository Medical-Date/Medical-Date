package medicaldate.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "ENFERMEDAD")
@Getter
@Setter
public class Enfermedad {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOMBRE")
	@NotNull
	private String nombre;

	@Column(name = "CAUSA")
	@NotNull
	private String causa;

	@Column(name = "SINTOMAS")
	@NotNull
	private String sintomas;

	@Column(name = "GRAVEDAD")
	@Enumerated(EnumType.STRING)
	@NotNull
	private Gravedad gravedad;

}
