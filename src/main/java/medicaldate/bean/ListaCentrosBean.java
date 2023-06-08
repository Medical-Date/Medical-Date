package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Centros;
import medicaldate.repository.CentrosRepository;
import medicaldate.services.CentrosServices;
import medicaldate.util.JsfUtils;


@Component
@ViewScoped
public class ListaCentrosBean implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	List<Centros> listaCentros;
	@Getter @Setter
	Centros centro;
	
	@Autowired
	private CentrosServices centrosServices;
	
	@Autowired
	private CentrosRepository centrosRepository;
	
	
	@PostConstruct
	public void init() {
		
		Long idCentros = (Long) JsfUtils.getFlashAttribute("idCentros");
		
		listaCentros= new ArrayList<Centros>();
		
		listaCentros= centrosServices.getCentros();
		
		if (idCentros != null) {
			centro = centrosServices.getCentrosById(idCentros);
		}
		
	}
	
	public void eliminarCentro(Centros centroSeleccionado) {
		if (centroSeleccionado != null) {
			centrosRepository.delete(centroSeleccionado);

			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
					.handleNavigation(FacesContext.getCurrentInstance(), null, "/listCentros.xhtml");
			
			FacesMessage msg = new FacesMessage("Centro borrado correctamente");
			FacesContext.getCurrentInstance().addMessage("formListadoCentros", msg);


		}
	}
	
	public void onEditar(Long idCentros) {
		JsfUtils.setFlashAttribute("idCentros", idCentros);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/editarCentros.xhtml");
	}
	
	public void editarCentro() {
		if (centro != null) {
			if(validacionesEditarCentro()) {
				centro = centrosRepository.save(centro);
				
				FacesContext.getCurrentInstance().addMessage(null, new 
						FacesMessage(FacesMessage.SEVERITY_INFO, "", "Centro editado correctamente"));

			}
		}
	}	
	
	public Boolean validacionesEditarCentro() {
		Boolean esValido=true;
		if(centro.getNombre().isBlank() || centro.getNombre().isEmpty() || centro.getCodigoPostal().isBlank() || centro.getCodigoPostal().isEmpty() || centro.getProvincia().isBlank() || centro.getProvincia().isEmpty() || centro.getLocalidad().isBlank() || centro.getLocalidad().isEmpty() || centro.getDireccion().isBlank() || centro.getDireccion().isEmpty() || centro.getTelefono().isBlank() || centro.getTelefono().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe rellenar los campos obligatorios"));
			esValido=false;
		}
		return esValido;
		
	}
	

}
