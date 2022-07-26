package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import medicaldate.model.Medico;
import medicaldate.model.MedicosCentro;
import medicaldate.model.MedicosCentroPaciente;
import medicaldate.model.MedicosPacientes;
import medicaldate.model.Paciente;
import medicaldate.repository.CentrosRepository;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.MedicosCentroPacienteRepository;
import medicaldate.repository.MedicosCentroRepository;
import medicaldate.repository.PacienteRepository;
import medicaldate.services.CentrosServices;
import medicaldate.services.MedicoService;
import medicaldate.services.MedicosCentroPacienteService;
import medicaldate.services.MedicosCentroService;
import medicaldate.services.PacienteService;

@Component
@ViewScoped
public class CentrosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private String nombre;
	@Getter
	@Setter
	private String codigoPostal;
	@Getter
	@Setter
	private String provincia;
	@Getter
	@Setter
	private String localidad;
	@Getter
	@Setter
	private String telefono;
	@Getter
	@Setter
	private String direccion;
	@Getter
	@Setter
	private Centros centro;
	@Autowired
	private CentrosServices centrosServices;

	@Autowired
	private CentrosRepository centrosRepository;

	@Getter
	@Setter
	private String medicoSelected;

	@Getter
	@Setter
	private List<Medico> listaMedicosPorNombreNoAsignados;

	@Getter
	@Setter
	private List<Centros> listaCentrosPorNombre;

	@Autowired
	private MedicoService medicoService;

	@Autowired
	private MedicoRepository medicoRepository;

	@Getter
	@Setter
	private String centroSelected;

	@Getter
	@Setter
	private Medico medico;

	@Autowired
	private MedicosCentroRepository medicosCentroRepository;
	@Autowired
	private MedicosCentroService medicosCentroService;

	@Getter
	@Setter
	private List<Paciente> listaPacientePorNombreSinCentroAsignado;

	@Autowired
	private MedicosCentroPacienteService medicosCentroPacienteService;
	
	@Autowired
	private MedicosCentroPacienteRepository medicosCentroPacienteRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;

	@Getter
	@Setter
	private List<MedicosCentro> listaMedicosCentroPorCentroSeleccionado;

	@Getter
	@Setter
	private List<String> listaMedicosPorCentroSeleccionado;
	
	@Getter
	@Setter
	private MedicosCentroPaciente medicosCentroPaciente;
	
	@Getter
	@Setter
	private Paciente paciente;
	
	@Getter
	@Setter
	private String pacienteSelected;
	
	@Autowired
	private PacienteService pacienteService;
	

	@PostConstruct
	public void init() {
		centro = new Centros();
		medico = new Medico();
		paciente=new Paciente();

		medicoSelected = "";

		listaMedicosPorCentroSeleccionado = new ArrayList<>();
		listaMedicosCentroPorCentroSeleccionado = new ArrayList<>();

		listaMedicosPorNombreNoAsignados = medicoService.getListaMedicosPorNombreNoAsignados();
		listaCentrosPorNombre = centrosServices.getListaCentrosPorNombre();

		listaPacientePorNombreSinCentroAsignado = pacienteService.obtenerListaPacientePorNombreSinCentroAsignado();

	}

	public void saveCentro() {
		centro = new Centros();

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

	public void asignarMedicoCentro() {
		MedicosCentro medicoCentro = new MedicosCentro();
		if (medico != null && centro != null) {
			Centros centrosSeleccionado = centrosServices.getCentrosPorNombre(centroSelected);
			Medico medicoSeleccionado = medicoService.getMedicosPorNombre(medicoSelected);
			centro = centrosSeleccionado;
			medico = medicoSeleccionado;

			if (medico == null) {
				FacesMessage msg = new FacesMessage("No hay médicos para asignar");
				FacesContext.getCurrentInstance().addMessage("formFormularioAsignarMedicoCentro", msg);
			} else {
				medicoCentro.setIdMedico(medico);
				medicoCentro.setIdCentro(centro);

				medicosCentroRepository.save(medicoCentro);

				medico.setEsAsignado(true);

				medicoRepository.save(medico);

				FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
						FacesContext.getCurrentInstance(), null, "/listMedicosCentrosAsignados.xhtml");

			}

		}

	}

	public Boolean noHayMedicos() {
		Boolean res = false;
		if (listaMedicosPorNombreNoAsignados.isEmpty()) {
			res = true;
		}
		return res;
	}

	public void onMedicoChange() {
		listaMedicosPorCentroSeleccionado = new ArrayList<>();
		if (centroSelected != null) {
			Centros c = centrosServices.getCentrosPorNombre(centroSelected);
			Long idCentro = c.getId();
			listaMedicosCentroPorCentroSeleccionado = medicosCentroService.obtenerMedicoPorCentro(idCentro);
			if (listaMedicosCentroPorCentroSeleccionado.isEmpty()) {
				listaMedicosPorCentroSeleccionado = new ArrayList<>();
			} else {
				for (MedicosCentro mC : listaMedicosCentroPorCentroSeleccionado) {
					listaMedicosPorCentroSeleccionado.add(mC.getIdMedico().getNombre());
				}
			}

		}
	}
	
	public void asignarCentroYMedico() {
		medicosCentroPaciente= new MedicosCentroPaciente();
		if(medico != null && paciente!=null && centro!=null) {
			Paciente pacienteSeleccionado = pacienteService.getPacientesPorNombre(pacienteSelected);
			Medico medicoSeleccionado = medicoService.getMedicosPorNombre(medicoSelected);
			Centros centroSeleccionado= centrosServices.getCentrosPorNombre(centroSelected);
			paciente= pacienteSeleccionado;
			medico=medicoSeleccionado;
			centro=centroSeleccionado;
			
			medicosCentroPaciente.setIdCentro(centroSeleccionado);
			medicosCentroPaciente.setIdMedico(medicoSeleccionado);
			medicosCentroPaciente.setIdPaciente(pacienteSeleccionado);
			
			medicosCentroPacienteRepository.save(medicosCentroPaciente);
			
			paciente.setTieneCentro(true);
			paciente.setTieneMedico(true);
			
			pacienteRepository.save(paciente);
			
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
			.handleNavigation(FacesContext.getCurrentInstance(), null, "/listaMedicosPacienteCentro.xhtml");
		}
		
	}


}
