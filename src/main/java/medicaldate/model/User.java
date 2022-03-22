package medicaldate.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean(name = "user")
@RequestScoped
public class User {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String dbPassword;
	private String dbName;
	DataSource ds;

	public User() {
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
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public String getDbName() {
		return dbName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
					
					if (con != null) {
						String sql = "INSERT INTO user(firstname, password, lastname, email) VALUES(?,?,?,?)";
						ps = con.prepareStatement(sql);
						ps.setString(1, firstName);
						ps.setString(2, password);
						ps.setString(3, lastName);
						ps.setString(4, email);
						i = ps.executeUpdate();
						System.out.println("Data Added Successfully");
					}
				
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					con.close();
					ps.close();
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
						String sql = "select firstname,password from user where firstname = '" + uName + "'";
						ps = con.prepareStatement(sql);
						rs = ps.executeQuery();
						rs.next();
						dbName = rs.getString("firstname");
						dbPassword = rs.getString("password");
					}
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			
		}
	}

	public String login() {
		dbData(firstName);
		if (firstName.equals(dbName) && password.equals(dbPassword)) {
			return "hola";
		} else
			return "unsuccess";
	}

	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/home.xhtml");
	}
}
