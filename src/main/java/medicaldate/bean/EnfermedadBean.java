package medicaldate.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import medicaldate.model.Gravedad;
import medicaldate.model.Historial;
import medicaldate.model.Paciente;
import medicaldate.model.User;
import medicaldate.repository.EnfermedadRepository;
import medicaldate.services.EnfermedadService;
import medicaldate.util.JsfUtils;

@Component
@ViewScoped
public class EnfermedadBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String nombre;

	@Getter
	@Setter
	private String causa;

	@Getter
	@Setter
	private String sintomas;

	@Getter
	@Setter
	private Gravedad gravedad;

	@Getter
	@Setter
	private Enfermedad enfermedad;

	@Getter
	@Setter
	private List<Enfermedad> listaEnfermedades;

	@Autowired
	private EnfermedadRepository enfermedadRepository;

	@Autowired
	private EnfermedadService enfermedadService;

	@PostConstruct
	public void init() {
		Long idEnfermedad = (Long) JsfUtils.getFlashAttribute("idEnfermedad");

		enfermedad = new Enfermedad();
		id = enfermedad.getId();
		nombre = enfermedad.getNombre();
		causa = enfermedad.getCausa();
		sintomas = enfermedad.getSintomas();
		gravedad = enfermedad.getGravedad();
		listaEnfermedades = new ArrayList<Enfermedad>();
		listaEnfermedades = enfermedadService.getEnfermedades();

		if (idEnfermedad != null) {
			enfermedad = enfermedadService.getEnfermedadById(idEnfermedad);
		}
	}
	
	public void guardarEnfermedad() {

		enfermedad = new Enfermedad();
		if (enfermedad != null) {
			enfermedad.setCausa(causa);
			enfermedad.setGravedad(gravedad);
			enfermedad.setNombre(nombre);
			enfermedad.setSintomas(sintomas);
			enfermedadRepository.save(enfermedad);
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
					.handleNavigation(FacesContext.getCurrentInstance(), null, "/listaEnfermedades.xhtml");
		}

	}

	public void editarEnfermedad() {
		if (enfermedad != null) {
			enfermedad = enfermedadRepository.save(enfermedad);
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
					.handleNavigation(FacesContext.getCurrentInstance(), null, "/listaEnfermedades.xhtml");
		}
	}

	public void onEditar(Long idEnfermedad) {
		JsfUtils.setFlashAttribute("idEnfermedad", idEnfermedad);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/editarEnfermedad.xhtml");
	}
	
	public void onEliminar(Long idEnfermedad) {
		JsfUtils.setFlashAttribute("idEnfermedad", idEnfermedad);
	}
	
	public void eliminarEnfermedad(Enfermedad enfermedadSeleccionada) {
		if (enfermedad != null) {
			enfermedadRepository.delete(enfermedadSeleccionada);
			
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
					.handleNavigation(FacesContext.getCurrentInstance(), null, "/listaEnfermedades.xhtml");

			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Enfermedad eliminada"));
		}
	}

}
