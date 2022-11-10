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
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "ENFERMEDAD_TRATAMIENTO")
@Getter
@Setter
public class EnfermedadTratamientos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "id_enfermedad", foreignKey = @ForeignKey(name = "FK_ENFERMEDADTRATAMIENTOS_ENFERMEDAD"))
	private Enfermedad idEnfermedad;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tratamientos", foreignKey = @ForeignKey(name = "FK_ENFERMEDADTRATAMIENTOS_TRATAMIENTOS"))
	private Tratamientos idTratamientos;


}
