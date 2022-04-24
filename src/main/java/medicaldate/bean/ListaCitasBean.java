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
import medicaldate.model.Cita;
import medicaldate.repository.CitaRepository;
import medicaldate.services.CitaService;

@Component
@ViewScoped
public class ListaCitasBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private List<Cita> listaCitas;
	
	@Autowired
	private CitaRepository citaRepository;
	@Autowired
	private CitaService citaService;
	
	
	@PostConstruct
	public void init() {
		
		listaCitas= new ArrayList<>();
		
		listaCitas=  citaService.getCitas();
		
	}

}
