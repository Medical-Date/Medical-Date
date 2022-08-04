package medicaldate.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
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
	@Getter
	@Setter
	String userName;
	@Getter
	@Setter
	String password;

	@Autowired
	UserService userService;

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
		userName = currentUser.getUserName();
		password = this.currentUser.getPassword();
		doLogin();
	}

	public void usuarioLogado() {
		User user = userService.getCurrentUser();

		user.getFirstName();
	}

	public String doLogin() {
		String result = "welcome.xhtml";

		Boolean esValido = validacionesGuardar();
		if (esValido) {

			User user = userService.obtenerUsuarioPorNombreUsuario(userName);
			if (user != null && user.getPassword().contentEquals(password)) {
				currentUser = user;
				UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userName,
						password);
				SecurityContext sc = SecurityContextHolder.getContext();
				sc.setAuthentication(authReq);
				clear();
			}
		} else {
			result = "login.xhtml";
		}

		return result;
	}

	private Boolean validacionesGuardar() {
		Boolean esValido = true;
		String camposValidos = "";
		String usuarioNoExiste = "";
		String contraseñaIncorrecta = "";

		if ((userName.isBlank() || userName == null) && (password.isBlank() || password == null)) {
			camposValidos = "Debe rellenar el nombre de usuario y la contraseña";
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", camposValidos));
			esValido = false;
		}

		if (!userName.isEmpty() && !(userService.existsUser(userName))) {
			usuarioNoExiste = "El usuario no existe";
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", usuarioNoExiste));
			esValido = false;
		}
		if (userName != null) {

			User user = userService.obtenerUsuarioPorNombreUsuario(userName);
			if (user != null && !(user.getPassword().contentEquals(password))) {
				contraseñaIncorrecta = "La contaseña es incorrecta";
				FacesContext.getCurrentInstance().addMessage(null, new 
						FacesMessage(FacesMessage.SEVERITY_ERROR, "", contraseñaIncorrecta));
				esValido = false;
			}
		}

		

		return esValido;
	}
	
	public String doLogout() {
		this.currentUser = null;
		return "index";
	}

	public Boolean existeUsuarioLogado() {
		Boolean res = false;
		if (this.currentUser == null) {
			return res;
		} else {
			res = true;
		}
		return res;
	}

	private void clear() {
		password = "";
		userName = "";
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
