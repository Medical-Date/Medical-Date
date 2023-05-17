package medicaldate.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import medicaldate.model.Calendario;
import medicaldate.model.CalendarioCitas;
import medicaldate.model.Centros;
import medicaldate.model.Cita;
import medicaldate.model.Enfermedad;
import medicaldate.model.Medico;
import medicaldate.model.MedicosCentro;
import medicaldate.model.MedicosCentroPaciente;
import medicaldate.model.MedicosPacientes;
import medicaldate.model.Paciente;
import medicaldate.model.Tratamientos;
import medicaldate.model.User;
import medicaldate.repository.CalendarioCitasRepository;
import medicaldate.repository.CalendarioRepository;
import medicaldate.repository.CitaRepository;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.PacienteRepository;
import medicaldate.services.CitaService;
import medicaldate.services.EnfermedadService;
import medicaldate.services.MedicoService;
import medicaldate.services.MedicosCentroPacienteService;
import medicaldate.services.MedicosPacientesService;
import medicaldate.services.PacienteService;
import medicaldate.services.TratamientosService;
import medicaldate.services.UserService;
import medicaldate.util.JsfUtils;

@Component
@ViewScoped
public class CitasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private Cita cita;
	@Getter
	@Setter
	private String medicoSelected;
	@Getter
	@Setter
	private String pacienteSelected;
	@Getter
	@Setter
	private List<Medico> listaMedicos;
	@Getter
	@Setter
	private List<Paciente> listaPacientes;

	@Getter
	@Setter
	private Date fechaCita;

	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private MedicoService medicoService;

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private CitaRepository citaRepository;
	@Getter
	@Setter
	private Calendario calendario;
	@Autowired
	private CalendarioRepository calendarioRepository;
	@Autowired
	private CalendarioCitasRepository calendarioCitasRepository;
	@Getter
	@Setter
	private List<Cita> listaCitasByMedicoLogado;
	@Getter
	@Setter
	private List<Cita> listaCitasByPacienteLogado;
	@Autowired
	private UserService userService;
	@Autowired
	private CitaService citaService;

	@Autowired
	private MedicosPacientesService medicosPacientesService;
	@Getter
	@Setter
	private Long idMedico;
	@Getter
	@Setter
	private Paciente paciente;
	@Autowired
	private PacienteService pacientesService;


	@Getter
	@Setter
	private List<Cita> listaMisCitas;

	@Autowired
	private MedicosCentroPacienteService medicosCentroPacienteService;
	@Getter
	@Setter
	private MedicosCentroPaciente medicoCentroPaciente;

	@Getter
	@Setter
	private List<String> listaEnfermedadesString;

	@Getter
	@Setter
	private List<Enfermedad> listaEnfermedades;

	@Autowired
	private EnfermedadService enfermedadService;

	@Getter
	@Setter
	private String enfermedadSelected;

	@Getter
	@Setter
	private List<Tratamientos> listaTratamiento;
	@Autowired
	private TratamientosService tratamientosService;
	
	@Getter
	@Setter
	private List<String> listaTratamientosString;
	
	@Getter
	@Setter
	private String tratamientosString;
	
	@Getter
	@Setter
	private String filtroPaciente;
	
	@Getter
	@Setter
	private List<String> listPacientesPorMedico;
	
	@Getter
	@Setter
	private User usuarioLogado;
	
	@Getter
	@Setter
	private Medico medicoLogado;


	@PostConstruct
	public void init() {
		Long idCita = (Long) JsfUtils.getFlashAttribute("idCita");
		calendario = new Calendario();
		listaTratamiento = new ArrayList<>();
		medicoCentroPaciente = new MedicosCentroPaciente();
		cita = new Cita();
		medicoSelected = "";
		pacienteSelected = "";
		listaMedicos = new ArrayList<>();
		listaPacientes = new ArrayList<>();
		listaMisCitas = new ArrayList<>();
		listaTratamientosString= new ArrayList<>();
		listPacientesPorMedico= new ArrayList<>();
		usuarioLogado= new User();
		medicoLogado= new Medico();

		listaMedicos = medicoService.getListaMedicosPorNombre();
		listaPacientes = pacienteService.getListaPacientesPorNombre();

		listaCitasByMedicoLogado = new ArrayList();
		usuarioLogado = userService.getCurrentUser();
		if (usuarioLogado != null) {
			listaCitasByMedicoLogado = citaService.getListaCitasByUsuario(usuarioLogado.getId());
			medicoLogado= medicoService.obtenerMedicoPorUsuario(usuarioLogado.getId());
			

			paciente = pacientesService.obtenerPacientePorUsuarioLogado(usuarioLogado.getId());
			
			if (paciente != null) {

				medicoCentroPaciente = medicosCentroPacienteService
						.obtenerMedicoCentroPacientePorPaciente(paciente.getId());

				listaMisCitas = citaService.getListaCitasByPaciente(paciente.getId());
				if (medicoCentroPaciente != null) {

					idMedico = medicoCentroPaciente.getIdMedico().getId();

					listaCitasByPacienteLogado = citaService.getListaCitasMedicoByPaciente(idMedico);
				}
			}

		}

		if (idCita != null) {
			cita = citaService.findById(idCita);
			if(cita.getEnfermedad()==null) {
				listaTratamiento= new ArrayList<>();
			}else if (cita.getEnfermedad().getId() != null || cita.getEnfermedad()!=null) {
			
				listaTratamiento = tratamientosService.getTratamientosByEnfermedad(cita.getEnfermedad().getId());
				
				if(!listaTratamiento.isEmpty()) {
					for(Tratamientos t: listaTratamiento) {
						listaTratamientosString.add(t.getDescripcion());
					}
				}
			}
		}

		listaEnfermedadesString = new ArrayList<>();
		listaEnfermedades = enfermedadService.getEnfermedades();
		if (!listaEnfermedades.isEmpty()) {
			for (Enfermedad e : listaEnfermedades) {
				listaEnfermedadesString.add(e.getNombre());
			}
		}
		
		obtenerListaPacientesPorMedico();

	}
	
	public List<String> obtenerListaPacientesPorMedico(){
		List<MedicosCentroPaciente> listMedicoCentroPaciente= new ArrayList<>();
		listMedicoCentroPaciente= medicosCentroPacienteService.obtenerListPacientePorMedico(medicoLogado.getId());
		
		for(MedicosCentroPaciente m: listMedicoCentroPaciente) {
			Paciente p= new Paciente();
			p=m.getIdPaciente();
			String nombreCompletoPaciente=p.getUser().getLastName()+","+ p.getUser().getFirstName();
			listPacientesPorMedico.add(nombreCompletoPaciente);
		}
		return listPacientesPorMedico;
		
	}

	public void crearCalendario() {

		Medico medicoSeleccionado = medicoService.getMedicosPorNombre(medicoSelected);
		Date diaInicio = calendario.getDiaInicio();
		Date diaFin = calendario.getDiaFin();
		Date horaEntrada = calendario.getHoraEntrada();
		Date horaSalida = calendario.getHoraSalida();
		calendario.setDiaInicio(diaInicio);
		calendario.setDiaFin(diaFin);
		calendario.setHoraEntrada(horaEntrada);
		calendario.setHoraSalida(horaSalida);
		calendario.setMedico(medicoSeleccionado);
		Integer horaSalidaInt = calendario.getHoraSalida().getHours();
		Integer horaEntradaInt = calendario.getHoraEntrada().getHours();
		calendarioRepository.save(calendario);

		if (horaSalidaInt - horaEntradaInt > 8) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"No puede crear un calendario con más de 8 horas diarias"));

		} else {
			// calendarioRepository.save(calendario);
			Double horario = null;
			horario = (double) (horaSalidaInt - horaEntradaInt);
			LocalTime horaEntrada2 = horaEntrada.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
			LocalTime horaSalida2 = horaSalida.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
			LocalDate diaInicio2 = diaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate diaFin2 = diaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			for(LocalDate j = diaInicio2; j.isBefore(diaFin2.plusDays(1)); j=j.plusDays(1)) {
			for (LocalTime i = horaEntrada2; i.isBefore(horaSalida2); i = i.plusMinutes(15)) {
				CalendarioCitas calendarioCitas = new CalendarioCitas();
				Cita citas = new Cita();
				calendarioCitas.setIdCalendario(calendario);
				citas.setDiaCita(j);
				citas.setHoraCita(i);
				citas.setMedico(medicoSeleccionado);
				citas.setDisponible(true);
				citaRepository.save(citas);
				calendarioCitas.setIdCitas(citas);
				calendarioCitas.setIdCalendario(calendario);

				calendarioCitasRepository.save(calendarioCitas);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Calendario y citas creados con éxito"));

			}
			}

		}

	}

	public String mostrarDiaCita(LocalDate diaCita) {
		String res = "";

		if (diaCita != null) {

			Integer dia = null;
			Integer mes = null;
			Integer anyo = null;

			LocalDate fecha = diaCita;

			dia = fecha.getDayOfMonth();
			mes = fecha.getMonthValue();
			anyo = fecha.getYear();
			res = dia + "/" + mes + "/" + anyo;
		}

		return res;
	}

	public String mostrarDisponibilidad(Boolean disp) {
		String res = "";
		if (disp != null) {
			if (disp.equals(true)) {
				res = "DISPONIBLE";
			} else {
				res = "NO DISPONIBLE";
			}
		}
		return res;

	}

	public void seleccionarCita(Cita cita) {
		cita.setPaciente(paciente);
		cita.setDisponible(false);
		citaRepository.save(cita);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Cita reservada con éxito"));

	}

	public void cancelarCita(Cita cita) {
		cita.setPaciente(null);
		cita.setDisponible(true);
		citaRepository.save(cita);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Cita cancelada con éxito"));
		listaMisCitas = citaService.getListaCitasByPaciente(paciente.getId());

	}

	public void gestionarCita(Long idCita) {
		JsfUtils.setFlashAttribute("idCita", idCita);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/gestionarCita.xhtml");

	}

	public void consultarCita(Long idCita) {
		JsfUtils.setFlashAttribute("idCita", idCita);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/consultarCita.xhtml");

	}

	public void guardarDiagnostico() {
		Enfermedad e = new Enfermedad();
		e = enfermedadService.getEnfermedadByNombre(enfermedadSelected);
		cita.setEnfermedad(e);
		citaRepository.save(cita);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Diagnóstico añadido con éxito"));
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/listCitas.xhtml");
	}
	
	public void tratamientoChange() {
		listaTratamientosString = new ArrayList<>();
		listaTratamiento= new ArrayList<>();
		tratamientosString="";
		if (enfermedadSelected != null) {
			Enfermedad e = enfermedadService.getEnfermedadByNombre(enfermedadSelected);
			Long idEnfermedad = e.getId();
			listaTratamiento = tratamientosService.getTratamientosByEnfermedad(idEnfermedad);
			if (listaTratamiento.isEmpty()) {
				listaTratamientosString = new ArrayList<>();
			} else {
				for (Tratamientos t : listaTratamiento) {
					listaTratamientosString.add(t.getDescripcion());
				}
				for(String s: listaTratamientosString) {
					tratamientosString+="\n"+s;
				}
			}

		}
	}
	
	public void filtroListaCitas() {
		Paciente p= new Paciente();
		if(!pacienteSelected.isEmpty()) {
		String[] trozos=pacienteSelected.split(",");
		String nombrePaciente=trozos[1];
		p= pacienteService.getPacientesPorNombre(nombrePaciente);
		listaCitasByMedicoLogado= citaService.getListaCitasByPaciente(p.getId());
		}
		
	}

}
