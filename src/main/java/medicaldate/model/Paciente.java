package medicaldate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "PACIENTE")
@Getter
@Setter
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name = "NOMBRE", unique = true)
	@NotNull
	private String nombre;
	
	@OneToOne
	@JoinColumn(name="cita_id")
	private Cita cita;
	
	@JoinColumn(name="tieneCentro")
	private Boolean tieneCentro;
	
	@JoinColumn(name="tieneMedico")
	private Boolean tieneMedico;


}
