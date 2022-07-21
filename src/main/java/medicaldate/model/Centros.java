package medicaldate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "CENTROS")
@Getter
@Setter
public class Centros {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NOMBRE")
	String nombre;
	
	@Column(name = "CODIGOPOSTAL")
	String codigoPostal;
	
	@Column(name = "PROVINCIA")
	String provincia;
	
	@Column(name = "LOCALIDAD")
	String localidad;
	
	@Column(name = "TELEFONO")
	String telefono;
	
	@Column(name = "DIRECCION")
	String direccion;
	
	

}
