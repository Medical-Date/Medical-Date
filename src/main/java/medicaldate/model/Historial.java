package medicaldate.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "HISTORIAL")
@Getter
@Setter
public class Historial {

	@Id
	@NotNull
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CONTACTOEMERGENCIA")
	@NotNull
	private String contactoEmergencia;

	@Column(name = "TLFCONTACTOEMERGENCIA")
	@NotNull
	private String tlfContactoEmergencia;

	@Column(name = "TIPOSANGRE")
	@Enumerated(EnumType.STRING)
	@NotNull
	private TipoSangre tipoSangre;

	@OneToOne
	@JoinColumn(name = "USUARIO_ID")
	@NotNull
	private User usuario;
	
	@JoinTable(name = "rel_historial_enfermedad", joinColumns = @JoinColumn(name = "FK_HISTORIAL", nullable = false), inverseJoinColumns = @JoinColumn(name = "FK_ENFERMEDAD", nullable = false))
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Enfermedad> enfermedad;

}
