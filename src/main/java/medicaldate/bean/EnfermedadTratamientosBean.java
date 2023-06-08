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
import medicaldate.model.Enfermedad;
import medicaldate.model.EnfermedadTratamientos;
import medicaldate.model.Tratamientos;
import medicaldate.repository.EnfermedadTratamientosRepository;
import medicaldate.repository.TratamientosRepository;
import medicaldate.services.EnfermedadService;
import medicaldate.services.TratamientosService;
import medicaldate.util.JsfUtils;

@Component
@ViewScoped
public class EnfermedadTratamientosBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Getter@Setter
	private Tratamientos tratamientos;
	@Autowired
	private TratamientosRepository tratamientosRepository;
	@Getter@Setter
	private List<Enfermedad> listaEnfermedades;
	@Autowired
	private EnfermedadService enfermedadService;
	@Getter@Setter
	private String enfermedadSelected;
	@Autowired
	private EnfermedadTratamientosRepository enfermedadTratamientosRepository;
	@Getter@Setter
	private EnfermedadTratamientos enfermedadTratamientos;
	@Getter@Setter
	private List<String> listaEnfermedadesString;
	@Getter@Setter
	private List<Tratamientos> listaTratamientosPorEnfermedad;
	@Autowired
	private TratamientosService tratamientosService;
	@Getter@Setter
	private Long idEnfermedad;
	
	
	
	@PostConstruct
	public void init() {
		Long idTratamiento = (Long) JsfUtils.getFlashAttribute("idTratamiento");
		tratamientos= new Tratamientos();
		listaEnfermedades= new ArrayList<>();
		listaEnfermedadesString= new ArrayList<>();
		listaEnfermedades= enfermedadService.getEnfermedades();
		if(!listaEnfermedades.isEmpty()) {
			for(Enfermedad e: listaEnfermedades) {
				listaEnfermedadesString.add(e.getNombre());
			}
		}
		
		if (idTratamiento != null) {
			tratamientos =tratamientosService.getTratamientosById(idTratamiento);
		}
		
	}
	
	
	public void crearTratamientos() {
		Enfermedad e= new Enfermedad();
		e= enfermedadService.getEnfermedadByNombre(enfermedadSelected);
		tratamientos.setEnfermedad(e);
		if(validacionesCrearTratamientos()) {
			tratamientosRepository.save(tratamientos);
			tratamientos= new Tratamientos();
			enfermedadSelected="";
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
			.handleNavigation(FacesContext.getCurrentInstance(), null, "/listaEnfermedades.xhtml");
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_INFO, "", "El tratamiento se ha creado correctamente"));
		}

		
	}
	
	public Boolean validacionesCrearTratamientos() {
		Boolean esValido=true;
		if(tratamientos.getEnfermedad()==null || tratamientos.getDescripcion().isBlank() || tratamientos.getDescripcion().isEmpty() || tratamientos.getNombre().isBlank() || tratamientos.getNombre().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe rellenar los campos obligatorios"));
			esValido=false;
		}
		return esValido;
		
	}
	public void seleccionarEnfermedad(Long idEnfermedad) {
		JsfUtils.setFlashAttribute("idEnfermedad", idEnfermedad);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
		.handleNavigation(FacesContext.getCurrentInstance(), null, "/listaTratamientosEnfermedad.xhtml");

		
	}
	
	public void onEditar(Long idTratamiento) {
		JsfUtils.setFlashAttribute("idTratamiento", idTratamiento);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/editarTratamiento.xhtml");
	}
	public void editarTratamientos() {
		if(validacionesEditarTratamientos()) {
			tratamientosRepository.save(tratamientos);
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_INFO, "", "El tratamiento se ha editado correctamente"));
		}
		

	}
	
	public Boolean validacionesEditarTratamientos() {
		Boolean esValido=true;
		if(tratamientos.getDescripcion().isBlank() || tratamientos.getDescripcion().isEmpty() || tratamientos.getNombre().isBlank() || tratamientos.getNombre().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe rellenar los campos obligatorios"));
			esValido=false;
		}
		return esValido;
	}
		
	public void eliminarTratamiento(Tratamientos t) {
		tratamientosRepository.delete(t);
		
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
		.handleNavigation(FacesContext.getCurrentInstance(), null, "/listaEnfermedades.xhtml");
		FacesContext.getCurrentInstance().addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_INFO, "", "El tratamiento se ha eliminado correctamente"));

	}

}
