package medicaldate.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Historial;
import medicaldate.model.Medico;
import medicaldate.model.MedicosCentroPaciente;
import medicaldate.model.Paciente;
import medicaldate.model.User;
import medicaldate.services.HistorialService;
import medicaldate.services.MedicoService;
import medicaldate.services.MedicosCentroPacienteService;
import medicaldate.services.UserService;

@Component
@ViewScoped
public class ListaHistorialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private List<Historial> listaHistorial;

	@Autowired
	private HistorialService historialService;

	@Autowired
	private MedicoService medicoService;
	@Autowired
	private MedicosCentroPacienteService medicoCentroPacienteService;
	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		listaHistorial = new ArrayList<Historial>();
		listHistorialesPacientePorMedico();

	}

	public void listHistorialesPacientePorMedico() {
		User user = userService.getCurrentUser();
		Medico medicoLogado = medicoService.obtenerMedicoPorUsuario(user.getId());
		if (medicoLogado != null) {

			List<MedicosCentroPaciente> listaPacientesPorMedicoCentro = medicoCentroPacienteService
					.obtenerListPacientePorMedico(medicoLogado.getId());
			List<Paciente> listaPacientes = new ArrayList<>();
			if (listaPacientesPorMedicoCentro.isEmpty()) {
				listaHistorial = new ArrayList<Historial>();
			} else {

				for (MedicosCentroPaciente mc : listaPacientesPorMedicoCentro) {
					listaPacientes.add(mc.getIdPaciente());
				}

				for (Paciente p : listaPacientes) {
					Historial hPorPaciente = new Historial();
					hPorPaciente = historialService.obtenerHistorialPorPaciente(p.getId());
					listaHistorial.add(hPorPaciente);
				}
			}
		} else {
			listaHistorial = new ArrayList<Historial>();
		}
	}

}
