package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Paciente;
import medicaldate.services.PacienteService;

@Component
@ViewScoped
public class PacienteBean implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private List<Paciente> listaPacientes;
	
	@Getter
	@Setter
	private String nombre;

	
	@Getter
	@Setter
	private Paciente paciente;
	
	@Autowired
	private PacienteService pacienteService;

	@PostConstruct
	public void init() {
		
		paciente= new Paciente();
		
		nombre= paciente.getNombre();
		
		listaPacientes= new ArrayList<>();
		
		listaPacientes= pacienteService.getPacientes();
		
	}

}
