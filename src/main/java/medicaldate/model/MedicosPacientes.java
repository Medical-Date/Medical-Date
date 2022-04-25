package medicaldate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "MEDICOS_PACIENTES")
@Getter
@Setter
public class MedicosPacientes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "id_medico", foreignKey = @ForeignKey(name = "FK_MEDICOSPACIENTES_MEDICO"))
	private Medico idMedico;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "id_perfil", foreignKey = @ForeignKey(name = "FK_MEDICOSPACIENTES_PACIENTE"))
	private Paciente idPaciente;

}
