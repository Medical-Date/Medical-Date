package medicaldate.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import lombok.extern.slf4j.Slf4j;
import medicaldate.model.User;
import medicaldate.repository.UserRepository;
import medicaldate.services.UserService;
@Component
@Slf4j
@ViewScoped
public class UserBean  implements Serializable{
	
	private static final String ELCAMPO= "el.campo";
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String firstName;
	
	@Getter
	@Setter
	private String lastName;
	
	@Getter
	@Setter
	private String password;
	
	@Getter
	@Setter
	private String email;
	
	@Getter
	@Setter
	private String dbName;
	
	@Getter
	@Setter
	private String dbPassword;
	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private String userName;
	@Getter
	@Setter
	private User usuarioConNombre;
	@Getter
	@Setter
	private List<User> listaUsuarios;
	
	@Autowired
	private UserService userService;
	@Autowired
    private UserRepository userRepository;
	
	
	
	@PostConstruct
	public void init() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/medicaldate?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "r00t-P@$$w0rd");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user= new User();
		userName= user.getUserName();
		firstName= user.getFirstName();
		lastName=user.getLastName();
		password= user.getPassword();
		email= user.getEmail();
		dbName=user.getDbName();
		dbPassword=user.getDbPassword();
		id=user.getId();
		listaUsuarios= new ArrayList<>();
		listaUsuarios=userService.getUsers();
		
		
		
		
		
		
		
		
	}
	
	public String add() {
		int i = 0;
		if (firstName != null) {
			PreparedStatement ps = null;
			Connection con = null;
			try {
				con = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/medicaldate?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "r00t-P@$$w0rd");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
					
					if (con != null ) {
						
						String sql = "INSERT INTO user(username,firstname, password, lastname, email) VALUES(?,?,?,?,?)";
							ps = con.prepareStatement(sql);
							if( validarRegistrar()) {
								ps.setString(1, userName);							
								ps.setString(2, firstName);
								ps.setString(3, password);
								ps.setString(4, lastName);
								ps.setString(5, email);
								i = ps.executeUpdate();
								System.out.println("Data Added Successfully");
								con.close();
								ps.close();
							}
							
						
							
						
						
					}
						 
					
				
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (i > 0) {
			return "success";
		} else
			return "unsuccess";
	}

	public void dbData(String uName) {
		if (uName != null) {
			PreparedStatement ps = null;
			Connection con = null;
			ResultSet rs = null;

			
				try {
					con = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/medicaldate?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "r00t-P@$$w0rd");
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
			return "hola";
		} else
			return "unsuccess";
	}

	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/home.xhtml");
	}
	private Boolean validarRegistrar() {	
		Boolean res= true;
			listaUsuarios= (List<User>) userRepository.findAll();
			if(!listaUsuarios.isEmpty()) {
				
			
			for(User u: listaUsuarios)
			if(u.getUserName().equals(userName)) {
				res=false;
				break;
			}else{
				res=true;
			}
			}
			
			
			return res;
			
			
		
	}
	
	public void listaUsuarios() {
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/listUsers.xhtml");
	}

}
