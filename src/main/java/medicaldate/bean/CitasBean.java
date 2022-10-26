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
import medicaldate.model.Calendario;
import medicaldate.model.CalendarioCitas;
import medicaldate.model.Cita;
import medicaldate.model.Medico;
import medicaldate.model.MedicosPacientes;
import medicaldate.model.Paciente;
import medicaldate.model.User;
import medicaldate.repository.CalendarioCitasRepository;
import medicaldate.repository.CalendarioRepository;
import medicaldate.repository.CitaRepository;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.PacienteRepository;
import medicaldate.services.CitaService;
import medicaldate.services.MedicoService;
import medicaldate.services.MedicosPacientesService;
import medicaldate.services.PacienteService;
import medicaldate.services.UserService;

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
	private MedicosPacientes medicosPacientes;
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

	@PostConstruct
	public void init() {
		calendario = new Calendario();
		medicosPacientes = new MedicosPacientes();
		cita = new Cita();
		medicoSelected = "";
		pacienteSelected = "";
		listaMedicos = new ArrayList<>();
		listaPacientes = new ArrayList<>();
		listaMisCitas= new ArrayList<>();

		listaMedicos = medicoService.getListaMedicosPorNombre();
		listaPacientes = pacienteService.getListaPacientesPorNombre();

		listaCitasByMedicoLogado = new ArrayList();
		User user = userService.getCurrentUser();
		if (user != null) {
			listaCitasByMedicoLogado = citaService.getListaCitasByUsuario(user.getId());

			paciente = pacientesService.obtenerPacientePorUsuarioLogado(user.getId());
			if (paciente != null) {
				medicosPacientes = medicosPacientesService.getMedicoByPacienteLogado(paciente.getId());
				listaMisCitas= citaService.getListaCitasByPaciente(paciente.getId());
				if (medicosPacientes != null) {

					idMedico = medicosPacientes.getIdMedico().getId();
					listaCitasByPacienteLogado = citaService.getListaCitasMedicoByPaciente(idMedico);
				}
			}

		}

	}

	public void crearCalendario() {

		Medico medicoSeleccionado = medicoService.getMedicosPorNombre(medicoSelected);
		Date dia = calendario.getDia();
		Date horaEntrada = calendario.getHoraEntrada();
		Date horaSalida = calendario.getHoraSalida();
		calendario.setDia(dia);
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

			for (LocalTime i = horaEntrada2; i.isBefore(horaSalida2); i = i.plusMinutes(15)) {
				CalendarioCitas calendarioCitas = new CalendarioCitas();
				Cita citas = new Cita();
				calendarioCitas.setIdCalendario(calendario);
				citas.setDiaCita(dia);
				citas.setHoraCita(i);
				citas.setMedico(medicoSeleccionado);
				citas.setDisponible(true);
				citaRepository.save(citas);
				calendarioCitas.setIdCitas(citas);
				calendarioCitas.setIdCalendario(calendario);
				calendarioCitas.setIdCitas(citas);

				calendarioCitasRepository.save(calendarioCitas);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Calendario y citas creados con éxito"));

			}

		}

	}

	public String mostrarDiaCita(Date diaCita) {
		String res = "";

		if (diaCita != null) {

			Integer dia = null;
			Integer mes = null;
			Integer anyo = null;

			LocalDate fecha = diaCita.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

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
		listaMisCitas= citaService.getListaCitasByPaciente(paciente.getId());
		
	}

}
