package medicaldate.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import medicaldate.model.User;
import medicaldate.services.UserService;
import medicaldate.util.JsfUtils;


@Controller("LoginController")
@SessionScope
public class LoginBackingBean {
	@Getter
	User currentUser;
	@Getter @Setter
	String userName;
	@Getter @Setter
	String password;
		
	@Autowired
	UserService userService;		
	
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
		userName=currentUser.getUserName();
		password=this.currentUser.getPassword();
		doLogin();
	}
	
	public void usuarioLogado() {
		User user= userService.getCurrentUser();
		
		user.getFirstName();
	}

	
	
	
	public String doLogin() {
		String result="welcome.xhtml";
						
		if(userService.existsUser(userName))
		{
			User user=userService.obtenerUsuarioPorNombreUsuario(userName);
			if(user!=null && user.getPassword().contentEquals(password)) {
				currentUser=user;
				UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userName, password);		
				SecurityContext sc = SecurityContextHolder.getContext();
				sc.setAuthentication(authReq);
				clear();
			}else {				
				FacesMessage msg=new FacesMessage("Password Invalid");
				//FacesContext.getCurrentInstance().addMessage(null, msg);
				JsfUtils.addErrorMessage("ERROR", "Contraseña incorrecta");
				result="login";
			}			
		}else {
			FacesMessage msg=new FacesMessage("User not found!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			result="login";
		}
		return result;	
	}
	
	public String doLogout() {
		this.currentUser=null;
		return "index";
	}
	
	private void clear() {
		password="";
		userName="";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentUser == null) ? 0 : currentUser.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userService == null) ? 0 : userService.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginBackingBean other = (LoginBackingBean) obj;
		if (currentUser == null) {
			if (other.currentUser != null)
				return false;
		} else if (!currentUser.equals(other.currentUser))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userService == null) {
			if (other.userService != null)
				return false;
		} else if (!userService.equals(other.userService))
			return false;
		return true;
	}	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4806972322119675590L;
	
}
