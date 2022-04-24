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

				String sql = "INSERT INTO enfermedad(nombre, causa, sintomas, gravedad) VALUES(?,?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, nombre);
				ps.setString(2, causa);
				ps.setString(3, sintomas);
				ps.setString(4, gravedad.toString());
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
