package medicaldate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "TRATAMIENTOS")
@Getter
@Setter
public class Tratamientos {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE")
    @NotNull
    private String nombre;

    @Column(name = "DESCRIPCION")
    @NotNull
    private String descripcion;

    @JoinColumn(name = "enfermedad_id")
    @OneToOne
    private Enfermedad enfermedad;

}