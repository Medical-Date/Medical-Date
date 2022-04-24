package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Medico;
import medicaldate.repository.MedicoRepository;
import medicaldate.services.MedicoService;

@Component
@ViewScoped
public class MedicoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private List<Medico> listaMedicos;
	
	@Getter
	@Setter
	private String nombre;

	
	@Getter
	@Setter
	private Medico medico;
	
	

	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@PostConstruct
	public void init() {
		
		medico= new Medico();
		
		
		
		listaMedicos= new ArrayList<>();
		
		listaMedicos= medicoService.getMedicos();
		
	}
	
	
	public void guardarMedico() {
		if (medico != null) {
			medico = medicoRepository.save(medico);
			
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
			.handleNavigation(FacesContext.getCurrentInstance(), null, "/listMedicos.xhtml");


		}
	}


}
