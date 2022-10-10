package medicaldate.bean;

import java.time.LocalDate;
import java.time.ZoneId;

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

	@Getter
	@Setter
	String newPassword;

	@Getter
	@Setter
	String repeatNewPassword;

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
			if (user.getRol().getRol().equals("USUARIO")) {
				result = "solicitudEspera.xhtml";
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
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", camposValidos));
			esValido = false;
		}

		if (!userName.isEmpty() && !(userService.existsUser(userName))) {
			usuarioNoExiste = "El usuario no existe";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", usuarioNoExiste));
			esValido = false;
		}
		if (userName != null) {

			User user = userService.obtenerUsuarioPorNombreUsuario(userName);
			if (user != null && !(user.getPassword().contentEquals(password))) {
				contraseñaIncorrecta = "La contaseña es incorrecta";
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", contraseñaIncorrecta));
				esValido = false;
			}
		}

		return esValido;
	}

	public Boolean validacionesReestablecerPassword() {
		Boolean esValido = true;
		String camposVacios = "";
		if ((password.isBlank() || password == null) || (newPassword.isBlank() || newPassword == null)
				|| (repeatNewPassword.isBlank() || repeatNewPassword == null)) {
			camposVacios = "Debe rellenar todos los campos";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", camposVacios));
			esValido = false;

		}

		return esValido;

	}
	
	private Boolean validacionesEditarMiPerfil() {
		Boolean esValido=true;
		String camposValidos="";
		
		if((currentUser.getUserName().isBlank() || currentUser.getUserName() == null) || 
			(currentUser.getFirstName().isBlank() || currentUser.getFirstName() == null) ||
			(currentUser.getLastName().isBlank() || currentUser.getLastName() == null) ||
			(currentUser.getDireccion().isBlank() || currentUser.getDireccion() == null) ||
			(currentUser.getTelefono().isBlank() || currentUser.getTelefono() == null) ||
			(currentUser.getEmail().isBlank() || currentUser.getEmail() == null) ) {
			camposValidos="Debe rellenar todos los campos obligatorios";

			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", camposValidos));
			esValido=false;
		}

		if(!currentUser.getTelefono().isBlank() && !(currentUser.getTelefono().startsWith("6")) && !(currentUser.getTelefono().startsWith("7")) && !(currentUser.getTelefono().startsWith("9")) && !(currentUser.getTelefono().startsWith("8"))) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El número de teléfono no es válido"));
			esValido=false;
		}
		if(!currentUser.getTelefono().isBlank() && currentUser.getTelefono().length()!=9) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El número de teléfono debe tener 9 dígitos"));
			esValido=false;
		}
		
		if(!currentUser.getEmail().isBlank() &&  !currentUser.getEmail().contains("@")) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Formato de email no válido"));
			esValido=false;
		}
		if(!userName.isBlank() &&  userName.length()<3) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre de usuario debe ser mayor de 2 caracteres"));
			esValido=false;
		}
		if(!currentUser.getFirstName().isBlank() &&  currentUser.getFirstName().length()<3) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre debe ser mayor de 2 caracteres"));
			esValido=false;
		}
		if(!currentUser.getLastName().isBlank() &&  currentUser.getLastName().length()<3) {
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El apellido debe ser mayor de 2 caracteres"));
			esValido=false;
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

	public String reestablecerPassword() {
		String res = "";

		String passNoCoinc = "";
		String nuevaPassNoCoinc = "";

		String passwordUsuarioLogado = currentUser.getPassword();

		Boolean esValido = validacionesReestablecerPassword();
		if (esValido) {

			if (!password.equals(passwordUsuarioLogado)) {

				passNoCoinc = "La contraseña actual no coincide";
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", passNoCoinc));
				res = "reestablecerPassword.xhtml";
			} else if (!newPassword.equals(repeatNewPassword)) {
				nuevaPassNoCoinc = "La nueva contraseña no coincide";
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", nuevaPassNoCoinc));
				res = "reestablecerPassword.xhtml";

			} else {

				currentUser.setPassword(newPassword);
				userService.saveUser(currentUser);
				FacesContext.getCurrentInstance().addMessage(null, new 
						FacesMessage(FacesMessage.SEVERITY_INFO, "", "Su contraseña se ha actualizado correctamente"));
				res = "miPerfil.xhtml";

			}

		}

		return res;

	}
	
	
	public void onEditar(Long idUsuario) {
		JsfUtils.setFlashAttribute("idUsuario", idUsuario);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/editarMiPerfil.xhtml");
	}
	

	public void editarUser() {
		
		Boolean esValido = validacionesEditarMiPerfil();
		if (esValido) {
		
		if (currentUser != null) {
			userService.saveUser(currentUser);
			
			FacesContext.getCurrentInstance().addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_INFO, "", "Su perfil se ha actualizado correctamente"));
			
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
			.handleNavigation(FacesContext.getCurrentInstance(), null, "/miPerfil.xhtml");
		}


		}
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
