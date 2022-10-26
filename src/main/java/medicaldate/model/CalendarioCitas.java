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
@Table(name = "CALENDARIO_CITAS")
@Getter
@Setter
public class CalendarioCitas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "id_calendario", foreignKey = @ForeignKey(name = "FK_CALENDARIOCITAS_CALENDARIO"))
	private Calendario idCalendario;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "id_citas", foreignKey = @ForeignKey(name = "FK_CALENDARIOCITAS_CITAS"))
	private Cita idCitas;

}
