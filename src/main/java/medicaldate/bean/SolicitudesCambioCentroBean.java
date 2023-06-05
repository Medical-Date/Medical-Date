package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Centros;
import medicaldate.model.Medico;
import medicaldate.model.MedicosCentro;
import medicaldate.model.MedicosCentroPaciente;
import medicaldate.model.SolicitudesCambioCentro;
import medicaldate.model.User;
import medicaldate.repository.SolicitudesCambioCentroRepository;
import medicaldate.services.CentrosServices;
import medicaldate.services.MedicoService;
import medicaldate.services.MedicosCentroPacienteService;
import medicaldate.services.MedicosCentroService;
import medicaldate.services.UserService;

@Component
@SessionScoped
public class SolicitudesCambioCentroBean implements Serializable {
	
	@Getter
	@Setter
	private Medico medicoLogado;
	@Getter
	@Setter
	private User userLogado;
	@Autowired
	private UserService userService;
	@Getter
	@Setter
	private Centros centroAnterior;
	@Autowired
	private MedicosCentroPacienteService medicosCentroPacienteService;
	@Getter
	@Setter
	private MedicosCentroPaciente medicoCentroPaciente;
	@Getter
	@Setter
	private Centros centroMedico;
	@Autowired
	private MedicosCentroService medicosCentroService;
	@Getter
	@Setter
	private List<MedicosCentroPaciente> listaMedicosCentroPaciente;
	@Getter
	@Setter
	private List<Medico> listaMedicosEnElCentroDelPaciente;
	@Getter
	@Setter
	private String medicoSelected;
	@Getter
	@Setter
	private SolicitudesCambioCentro solicitudCambioCentro;
	@Autowired
	private SolicitudesCambioCentroRepository solicitudesCambioCentroRepository;
	@Autowired
	private MedicoService medicoService;
	@Getter
	@Setter
	private List<Centros> listaCentros;
	@Getter
	@Setter
	private List<MedicosCentro> listaMedicosCentro;
	@Autowired
	private CentrosServices centroService;
	@Getter
	@Setter
	private String centroSelected;
	
	@Getter
	@Setter
	private MedicosCentro medicoCentro;
	
	@Getter
	@Setter
	private List<String> listaCentrosPorNombre;
	
	private static final long serialVersionUID = 1L;

	@PostConstruct
	//TODO
	public void init() {

		solicitudCambioCentro= new SolicitudesCambioCentro();
		userLogado = userService.getCurrentUser();
		medicoLogado= new Medico();
		medicoLogado = medicoService.obtenerMedicoPorUsuario(userLogado.getId());
		medicoCentroPaciente= new MedicosCentroPaciente();
		centroMedico= new Centros();
		listaMedicosCentro= new ArrayList<>();
		centroAnterior= new Centros();
		listaCentros= new ArrayList<>();
		listaCentrosPorNombre= new ArrayList<>();
		

		if (medicoLogado != null) {
			medicoCentro= medicosCentroService.obtenerMedico(medicoLogado.getId());
			centroAnterior=medicoCentro.getIdCentro();
			listaCentros= centroService.getCentros();			
		}
		if(!listaCentros.isEmpty()) {
			for(Centros c: listaCentros) {
				listaCentrosPorNombre.add(c.getNombre());
			}
			listaCentrosPorNombre.remove(centroAnterior.getNombre());
		}

	}
	
	public String enviarSolicitud() {
		String res="";
		Centros nuevoCentro = new Centros();
		nuevoCentro= centroService.getCentrosPorNombre(centroSelected);
		solicitudCambioCentro.setCentros(nuevoCentro);
		solicitudCambioCentro.setMedico(medicoLogado);
		solicitudesCambioCentroRepository.save(solicitudCambioCentro);
		res="welcome.xhtml";
		FacesContext.getCurrentInstance().addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_INFO, "", "Su solicitud se ha enviado correctamente"));
		return res;
	}

}
