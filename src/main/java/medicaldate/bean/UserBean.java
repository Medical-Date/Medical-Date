package medicaldate.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.Medico;
import medicaldate.model.Paciente;
import medicaldate.model.Rol;
import medicaldate.model.Roles;
import medicaldate.model.User;
import medicaldate.repository.MedicoRepository;
import medicaldate.repository.PacienteRepository;
import medicaldate.repository.UserRepository;
import medicaldate.services.RolService;
import medicaldate.services.UserService;
import medicaldate.util.JsfUtils;

@Component
@ViewScoped
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
//	
//	DataSource ds;
//	
//	 public UserBean() {
//	        try {
//	            Context ctx = new InitialContext();
//	            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");
//	        } catch (NamingException e) {
//	            e.printStackTrace();
//	        }
//	    }
	

	
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

	

}