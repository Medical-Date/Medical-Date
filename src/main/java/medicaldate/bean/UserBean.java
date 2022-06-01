package medicaldate.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Rol;
import medicaldate.model.User;
import medicaldate.services.PacienteService;
import medicaldate.services.UserService;


@Component
@SessionScoped
@ManagedBean(name = "user")
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String password;


	@Getter
	@Setter
	private String dbName;

	@Getter
	@Setter
	private String dbPassword;


	@Getter
	@Setter
	private String userName;
	
	@Setter
	private String nombreCompleto;
	
	@Getter
	@Setter
	private User user;
	
	@Autowired
	private UserService userService;

	public void dbData(String uName) {
		if (uName != null) {
			PreparedStatement ps = null;
			Connection con = null;
			ResultSet rs = null;

			try {
				con = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/medicaldate?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
						"root", "r00t-P@$$w0rd");
				if (con != null) {
					String sql = "select userName,password from user where userName = '" + uName + "'";
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					rs.next();
					dbName = rs.getString("userName");
					dbPassword = rs.getString("password");
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}

		}
	}

	public String login() {
		dbData(userName);
		if (userName.equals(dbName) && password.equals(dbPassword)) {
			return "welcome";
		} else
			return "unsuccess";
	}

	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/home.xhtml");
	}
	
	public void inicio() {
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/home.xhtml");
	}
	
	public User getUser() {
		user= new User();
		
		user= userService.obtenerUsuarioPorNombreUsuario(userName);
		return user;
	}
	
	

	

}