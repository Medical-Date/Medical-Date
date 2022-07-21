package medicaldate.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Centros;
import medicaldate.repository.CentrosRepository;
import medicaldate.services.CentrosServices;

@Component
@ViewScoped
public class CentrosBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Getter @Setter
	private String nombre;
	@Getter @Setter
	private String codigoPostal;
	@Getter @Setter
	private String provincia;	
	@Getter @Setter
	private String localidad;	
	@Getter @Setter
	private String telefono;	
	@Getter @Setter
	private String direccion;
	@Getter @Setter
	private Centros centro;
	@Autowired
	private CentrosServices centrosServices;
	
	@Autowired
	private CentrosRepository centrosRepository;
	
	
	@PostConstruct
	public void init() {
		centro= new Centros();
		
		
	}
	
	
	public void saveCentro() {
		centro= new Centros();
		
		centro.setNombre(nombre);
		centro.setCodigoPostal(codigoPostal);
		centro.setProvincia(provincia);
		centro.setLocalidad(localidad);
		centro.setDireccion(direccion);
		centro.setTelefono(telefono);
		
		centrosRepository.save(centro);
		
		FacesMessage msg = new FacesMessage("Centro creado con éxito");
		FacesContext.getCurrentInstance().addMessage("formListadoCentros", msg);
		
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
		.handleNavigation(FacesContext.getCurrentInstance(), null, "/listCentros.xhtml");
	}

}
