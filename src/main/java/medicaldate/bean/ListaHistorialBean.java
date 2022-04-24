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
import medicaldate.model.Historial;
import medicaldate.services.HistorialService;

@Component
@ViewScoped
public class ListaHistorialBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private List<Historial> listaHistorial;
	
	@Autowired
	private HistorialService historialService;
	
	@PostConstruct
	public void init() {
		listaHistorial = new ArrayList<Historial>();
		listaHistorial = historialService.getHistoriales();
		
	}

}
