package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.MedicosCentro;
import medicaldate.services.MedicosCentroService;

@Component
@SessionScoped
public class MedicosCentroBean implements Serializable{
		
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private List<MedicosCentro> listaMedicosCentro;
	
	@Autowired
	private MedicosCentroService medicosCentroService;
	
	@PostConstruct
	public void init() {
		
		listaMedicosCentro= new ArrayList<>();
		listaMedicosCentro= medicosCentroService.getMedicosCentro();
		
	}

}
