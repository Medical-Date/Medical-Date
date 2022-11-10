package medicaldate.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Tratamientos;
import medicaldate.services.TratamientosService;
import medicaldate.util.JsfUtils;

@Component
@ViewScoped
public class ListaTratamientosPorEnfermedadBean implements Serializable{
	private static final long serialVersionUID = 1L;
	@Getter@Setter
	private List<Tratamientos> listaTratamientosPorEnfermedad;
	

	@Autowired
	private TratamientosService tratamientosService;
	
	@PostConstruct
	public void init() {
		
		Long idEnfermedad = (Long) JsfUtils.getFlashAttribute("idEnfermedad");
		
		listaTratamientosPorEnfermedad=  tratamientosService.getTratamientosByEnfermedad(idEnfermedad);
		
	}

}
