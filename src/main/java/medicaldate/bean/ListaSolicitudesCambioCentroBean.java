package medicaldate.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.CalendarioCitas;
import medicaldate.model.Centros;
import medicaldate.model.Cita;
import medicaldate.model.Medico;
import medicaldate.model.MedicosCentro;
import medicaldate.model.MedicosCentroPaciente;
import medicaldate.model.Paciente;
import medicaldate.model.SolicitudesCambioCentro;
import medicaldate.model.SolicitudesCambioMedico;
import medicaldate.repository.CalendarioCitasRepository;
import medicaldate.repository.CitaRepository;
import medicaldate.repository.MedicosCentroPacienteRepository;
import medicaldate.repository.MedicosCentroRepository;
import medicaldate.repository.SolicitudesCambioCentroRepository;
import medicaldate.services.CalendarioCitasService;
import medicaldate.services.CitaService;
import medicaldate.services.MedicosCentroPacienteService;
import medicaldate.services.MedicosCentroService;
import medicaldate.services.SolicitudesCambioCentroService;
@Component
@SessionScoped
public class ListaSolicitudesCambioCentroBean  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private List<SolicitudesCambioCentro> listaSolicitudesCambioCentro;
	@Getter
	@Setter
	private List<SolicitudesCambioCentro> listaSolicitudesCambioCentroRechazadas;
	@Getter
	@Setter
	private List<SolicitudesCambioCentro> listaSolicitudesCambioCentroAceptadas;
	@Getter @Setter
	private SolicitudesCambioCentro solicitudesCambioCentro;
	
	@Autowired
	private SolicitudesCambioCentroRepository solicitudesCambioCentroRepository;
	@Autowired
	private SolicitudesCambioCentroService solicitudesCambioCentroService;
	@Getter
	@Setter
	private Centros centroMedicoAnterior;
	@Getter
	@Setter
	private MedicosCentro medicoCentroAnterior;
	@Getter
	@Setter
	private MedicosCentro medicoCentroCambiado;
	@Autowired
	private MedicosCentroService medicosCentroService;
	
	@Getter
	@Setter
	private MedicosCentroPaciente medicoCentroPaciente;
	@Autowired
	private MedicosCentroPacienteService medicosCentroPacienteService;
	@Autowired
	private MedicosCentroPacienteRepository medicosCentroPacienteRepository;
	
	@Autowired
	private MedicosCentroRepository medicosCentroRepository;
	
	@Getter
	@Setter
	private Centros centroAnterior;
	@Autowired
	private CitaService citaService;
	@Autowired
	private CitaRepository citaRepository;
	@Autowired
	private CalendarioCitasRepository calendarioCitasRepository;
	@Autowired
	private CalendarioCitasService calendarioCitasService;
	@Getter
	@Setter
	private CalendarioCitas calendarioCitas;
	
	
	@PostConstruct
	public void init() {
		cargarSolicitudesCambioCentro();
	}
	
	public String obtenerCentroAnterior(Medico idMedico) {
		String res="";
		medicoCentroAnterior = medicosCentroService.obtenerMedico(idMedico.getId());
		centroAnterior= medicoCentroAnterior.getIdCentro();
		res=centroAnterior.getNombre();
		return res;
	}
	
	public void cargarSolicitudesCambioCentro() {
		listaSolicitudesCambioCentro= new ArrayList<>();
		listaSolicitudesCambioCentroRechazadas= new ArrayList<>();
		listaSolicitudesCambioCentroAceptadas= new ArrayList<>();
		solicitudesCambioCentro= new SolicitudesCambioCentro();
		listaSolicitudesCambioCentro=solicitudesCambioCentroService.listaSolicitudesCambioCentroPendientes();
		listaSolicitudesCambioCentroRechazadas=solicitudesCambioCentroService.listaSolicitudesCambioCentroRechazadas();
		listaSolicitudesCambioCentroAceptadas=solicitudesCambioCentroService.listaSolicitudesCambioCentroAceptadas();
	}
	
	public String estadoSolicitudCambioCentro(SolicitudesCambioCentro solCambiCentro) {
		String res="";
		if(solCambiCentro.getEstado()!=null &&    solCambiCentro.getEstado().equals(false)) {
			res="RECHAZADA";
		}else if(solCambiCentro.getEstado()!=null && solCambiCentro.getEstado().equals(true)) {
			res="ACEPTADA";
		}else {
			res="PENDIENTE";
		}
		return res;
	}
	
	public void aceptarSolicitudCambioCentro(SolicitudesCambioCentro solCambiCentro) {
		solCambiCentro.setEstado(true);

		if (comprobarSiHayMasMedicos(solCambiCentro.getMedico())) {
			// MODIFICAMOS TABLA MEDICO-CENTRO
			solicitudesCambioCentroRepository.save(solCambiCentro);
			medicoCentroCambiado = medicosCentroService.obtenerMedico(solCambiCentro.getMedico().getId());
			medicoCentroCambiado.setIdCentro(solCambiCentro.getCentros()); // centro nuevo
			medicosCentroRepository.save(medicoCentroCambiado);

			// MODIFICACMOS TABLA MEDICO-CENTRO-PACIENTE

			// cogemos los medicos que estan en el mismo centro que los pacientes del medico
			// modificado
			// les asignamos un medico aleatorio de ese centro
			// obtenemos la lista de pacientes que van a ser modificadas por el cambio de
			// medico
			List<MedicosCentroPaciente> listaMedicoCentroPaciente = medicosCentroPacienteService
					.obtenerListPacientePorMedico(solCambiCentro.getMedico().getId());
			if (!listaMedicoCentroPaciente.isEmpty()) {
				centroMedicoAnterior = listaMedicoCentroPaciente.get(0).getIdCentro();
				// obtenemos la lista de medicos que van a ser asignados a esos pacientes que
				// quedan libre (mismo centro)
				List<MedicosCentro> listMedicoPorCentro = medicosCentroService
						.obtenerMedicoPorCentro(centroMedicoAnterior.getId());
				MedicosCentro mcActual = medicosCentroService.obtenerMedico(solCambiCentro.getMedico().getId());
				listMedicoPorCentro.remove(mcActual);
				Random aleatorio = new Random();
				MedicosCentro r = listMedicoPorCentro.get(aleatorio.nextInt(listMedicoPorCentro.size()));

				for (MedicosCentroPaciente mcp : listaMedicoCentroPaciente) {
					mcp.setIdMedico(r.getIdMedico());
					medicosCentroPacienteRepository.save(mcp);
				}

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "", "La solicitud se ha aceptado correctamente"));
				listaSolicitudesCambioCentro.remove(solCambiCentro);
				listaSolicitudesCambioCentroAceptadas.add(solCambiCentro);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "", "La solicitud se ha aceptado correctamente"));
				listaSolicitudesCambioCentro.remove(solCambiCentro);
				listaSolicitudesCambioCentroAceptadas.add(solCambiCentro);
			}
			// eliminamos las citas futuras del medico modificado

			List<Cita> citasPenditesMedico = new ArrayList<>();
			citasPenditesMedico = citaService.getListaCitasMedicoByPaciente(solCambiCentro.getMedico().getId());
			if (!citasPenditesMedico.isEmpty()) {
				LocalDate fechaActual = LocalDate.now();
				for (Cita c : citasPenditesMedico) {
					if (c.getDiaCita().isAfter(fechaActual) || (c.getDiaCita().isBefore(fechaActual) && c.getPaciente()==null)) {
						CalendarioCitas calendario= new CalendarioCitas();
						calendario= calendarioCitasService.obtenerCalendarioPorCita(c.getId());
						calendarioCitasRepository.delete(calendario);
						citaRepository.delete(c);
					}
				}
			}

		}

	}
	
	public Boolean comprobarSiHayMasMedicos(Medico medico) {
		Boolean esValido=true;
		MedicosCentro medicoCentroAntiguo= medicosCentroService.obtenerMedico(medico.getId());
		Centros centroAntiguo= medicoCentroAntiguo.getIdCentro();
		List<MedicosCentro> listCentroPorMedico=medicosCentroService.obtenerMedicoPorCentro(centroAntiguo.getId());
		if(listCentroPorMedico.size()==1) {
			esValido=false;
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede aceptar la solicitud. No hay más médicos disponibles en el centro"));
		}
		
		
		
		
		return esValido;
	}
	
	public void rechazarSolicitudCambioCentro(SolicitudesCambioCentro solCambiCentro) {
		solCambiCentro.setEstado(false);
		solicitudesCambioCentroRepository.save(solCambiCentro);
		listaSolicitudesCambioCentroRechazadas.add(solCambiCentro);
		listaSolicitudesCambioCentro.remove(solCambiCentro);
		FacesContext.getCurrentInstance().addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_INFO, "", "La solicitud se ha rechazado correctamente"));
	}

}
