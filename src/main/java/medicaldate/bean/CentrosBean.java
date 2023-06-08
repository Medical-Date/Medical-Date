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
	
	@Getter
	@Setter
	private List<Paciente> listaPacienteSinMedicoYCentro;
	

	@PostConstruct
	public void init() {
		centro = new Centros();
		medico = new Medico();
		paciente=new Paciente();

		medicoSelected = "";

		listaMedicosPorCentroSeleccionado = new ArrayList<>();
		listaMedicosCentroPorCentroSeleccionado = new ArrayList<>();
		listaPacienteSinMedicoYCentro= new ArrayList<>();

		listaMedicosPorNombreNoAsignados = medicoService.getListaMedicosPorNombreNoAsignados();
		listaCentrosPorNombre = centrosServices.getListaCentrosPorNombre();

		listaPacienteSinMedicoYCentro = pacienteService.obtenerListaPacientePorNombreSinCentroAsignadoSinMedico();
		
		//solo pacientes sin medico
		

	}

	public void saveCentro() {
		centro = new Centros();
		if(validacionesCrearCentro()) {
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
	
	public Boolean validacionesCrearCentro() {
		Boolean esValido=true;
		if(nombre.isBlank() || nombre.isEmpty() || codigoPostal.isBlank() || codigoPostal.isEmpty() || provincia.isBlank() || provincia.isEmpty() || localidad.isBlank() || localidad.isEmpty() || direccion.isBlank() || direccion.isEmpty() || telefono.isBlank() || telefono.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe rellenar los campos obligatorios"));
			esValido=false;
		}
		return esValido;
		
	}

	public void asignarMedicoCentro() {
		MedicosCentro medicoCentro = new MedicosCentro();
		if (medico != null && centro != null) {
			Centros centrosSeleccionado = centrosServices.getCentrosPorNombre(centroSelected);
			Medico medicoSeleccionado = medicoService.getMedicosPorNombre(medicoSelected);
			centro = centrosSeleccionado;
			medico = medicoSeleccionado;

			if (medico == null) {
				FacesContext.getCurrentInstance().addMessage(null, new 
						FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No hay médicos para asignar"));
			}else if(centro==null) {
				FacesContext.getCurrentInstance().addMessage(null, new 
						FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe elegir un centro"));
			} else {
				medicoCentro.setIdMedico(medico);
				medicoCentro.setIdCentro(centro);

				medicosCentroRepository.save(medicoCentro);

				medico.setEsAsignado(true);

				medicoRepository.save(medico);
				
				centro=null;
				medico=null;

				FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
						FacesContext.getCurrentInstance(), null, "/listMedicosCentrosAsignados.xhtml");
				
				FacesContext.getCurrentInstance().addMessage(null, new 
						FacesMessage(FacesMessage.SEVERITY_INFO, "", "Centro asignado al médico correctamente"));

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
			if(validacionAsignarCentroYMedico()) {
				medicosCentroPaciente.setIdCentro(centroSeleccionado);
				medicosCentroPaciente.setIdMedico(medicoSeleccionado);
				medicosCentroPaciente.setIdPaciente(pacienteSeleccionado);
				
				medicosCentroPacienteRepository.save(medicosCentroPaciente);
				
				paciente.setTieneCentro(true);
				paciente.setTieneMedico(true);
				
				pacienteRepository.save(paciente);
				
				medico=new Medico();
				paciente= new Paciente();
				listaCentrosPorNombre= new ArrayList<>();
				
				FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/listaMedicosPacienteCentro.xhtml");
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Médico y centro asignado a paciente correctamente"));
			}
			
			
		}
		
	}
	
	public Boolean validacionAsignarCentroYMedico() {
		Boolean esValido=true;
		if(paciente==null || medico==null || centro == null) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe rellenar los campos obligatorios"));
			esValido=false;
		}
		return esValido;
		
	}


}
