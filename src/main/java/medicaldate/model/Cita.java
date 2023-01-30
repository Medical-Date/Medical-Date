package medicaldate.model;

import java.time.LocalDate;
import java.time.LocalTime;
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

import org.hibernate.annotations.ColumnDefault;

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
	private Long id;

	@Column(name = "DIA_CITA")
	private LocalDate diaCita;

	@Column(name = "HORA_CITA")
	private LocalTime horaCita;

	@OneToOne
	@JoinColumn(name = "id_medico")
	private Medico medico;

	@OneToOne
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;

	@Column(name = "disponible")
	@ColumnDefault("1")
	private Boolean disponible;

	@OneToOne
	@JoinColumn(name = "id_enfermedad")
	private Enfermedad enfermedad;

	@Column(name = "COMENTARIO")
	private String comentario;

}
