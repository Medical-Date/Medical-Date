package medicaldate.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Enfermedad;
import medicaldate.model.Historial;
import medicaldate.model.TipoSangre;
import medicaldate.model.User;
import medicaldate.repository.HistorialRepository;
import medicaldate.repository.UserRepository;
import medicaldate.services.EnfermedadService;
import medicaldate.services.HistorialService;
import medicaldate.util.JsfUtils;

@Component
@ViewScoped
public class HistorialBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String contactoEmergencia;

	@Getter
	@Setter
	private String tlfContactoEmergencia;

	@Getter
	@Setter
	private TipoSangre tipoSangre;

	@Getter
	@Setter
	private User usuario;
	
	@Getter
	@Setter
	private Historial historial;

	@Getter
	@Setter
	private List<Historial> listaHistorial;
	
	@Getter
	@Setter
	private List<Enfermedad> listaEnfermedad;
	
	@Autowired
	private HistorialRepository historialRepository;

	@Autowired
	private HistorialService historialService;
	
	@Autowired
	private EnfermedadService enfermedadService;
	

	@PostConstruct
	public void init() {
		Long idHistorial = (Long) JsfUtils.getFlashAttribute("idHistorial");
		
		historial = new Historial();
		id = historial.getId();
		contactoEmergencia = historial.getContactoEmergencia();
		tlfContactoEmergencia = historial.getTlfContactoEmergencia();
		tipoSangre = historial.getTipoSangre();
		usuario = new User();
		usuario = historial.getUsuario();
		listaHistorial = new ArrayList<Historial>();
		listaHistorial = historialService.getHistoriales();
		listaEnfermedad = new ArrayList<Enfermedad>();
		listaEnfermedad = enfermedadService.getEnfermedades();
		
		if (idHistorial != null) {
			historial = historialService.getHistorialById(idHistorial);
		}
	}

	public String add() {
		int i = 0;
		PreparedStatement ps = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/medicaldate?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "r00t-P@$$w0rd");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			if (con != null) {

				String sql = "INSERT INTO historial(contactoEmergencia,tlfContactoEmergencia, tipoSangre, usuario_id) VALUES(?,?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, contactoEmergencia);
				ps.setString(2, tlfContactoEmergencia);
				ps.setString(3, tipoSangre.toString());
				ps.setLong(4, usuario.getId());
				i = ps.executeUpdate();
				System.out.println("Data Added Successfully");
				con.close();
				ps.close();
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (i > 0) {
			return "success";
		} else
			return "unsuccess";
	}
	
	public void editarHistorial() {
		if (historial != null) {
			historial = historialRepository.save(historial);		
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
			.handleNavigation(FacesContext.getCurrentInstance(), null, "/listaHistoriales.xhtml");
		}
	}

	public void onEditar(Long idHistorial) {
		JsfUtils.setFlashAttribute("idHistorial", idHistorial);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/editarHistorial.xhtml");
	}

}
