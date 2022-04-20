package medicaldate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "CITA")
@Getter
@Setter
public class Cita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@NotNull
	private Long id;
	
	@Column(name = "FECHACITA")
	private Date fechaCita;
	
	@OneToOne
	@JoinColumn(name="medico_id")
	private Medico medico;
	
	@OneToOne
	@JoinColumn(name="paciente_id")
	private Paciente paciente;

}
