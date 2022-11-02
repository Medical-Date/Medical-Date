package medicaldate.model;

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
@Table(name = "SOLICITUDES_CAMBIO_MEDICO")
@Getter
@Setter
public class SolicitudesCambioMedico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="paciente_id")
	private Paciente paciente;
	
	@OneToOne
	@JoinColumn(name="medico_nuevo_id")
	private Medico medico;
	
	@NotNull
	@Column(name = "ESTADO")
	private Boolean estado;
	
	@NotNull
	@Column(name = "MOTIVO")
	private String motivo;
	
}

