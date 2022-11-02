package medicaldate.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class JsfUtils {

	/** Constructor basico. */
	private JsfUtils() {
		super();
	}	
	private static final Pattern REGEXP = Pattern.compile("[0-9]{8}[A-Z]");
	private static final String DIGITO_CONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";
	private static final String[] INVALIDOS = new String[] { "00000000T", "00000001R", "99999999R" };

	/**
	 * Recupera el atributo de sesion con el nombre 'name'.
	 * 
	 * @param name String
	 * @return Object
	 */
	public static Object getSessionAttribute(final String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
	}

	/**
	 * Asigna al atributo de sesion con el nombre 'name' con el valor del objeto
	 * 'obj'.
	 * 
	 * @param name String
	 * @param obj  Object
	 */
	public static void setSessionAttribute(final String name, final Object obj) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(name, obj);
	}

	/**
	 * Elimina el atributo de sesion con el nombre 'name'.
	 * 
	 * @param name String
	 */
	public static void removeAttribute(final String name) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(name);
	}

	/**
	 * Recupera el atributo de flash con el nombre 'name'.
	 * 
	 * @param name String
	 * @return Object
	 */
	public static Object getFlashAttribute(final String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash().get(name);
	}

	/**
	 * Asigna al atributo de flash con el nombre 'name' con el valor del objeto
	 * 'obj'.
	 * 
	 * @param name String
	 * @param obj  Object
	 */
	public static void setFlashAttribute(final String name, final Object obj) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put(name, obj);
	}
	
	public static void addErrorMessages(List<String> messages) {
		for (String message : messages){
			addErrorMessage(message);
		}
	}


	public static FacesMessage addErrorMessage(String title, String msg) {
		log.error(msg);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);

		return facesMsg;
	}

	
	public static void addErrorMessage(String msg) {
		log.error(msg);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}


	public static void addSuccessMessage(String title, String msg) {
		log.info(msg);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}


	public static void addInfoMessage(String title, String msg) {
		log.warn(msg);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, title, msg);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}
	
	public static void addInfoMessage(String msg) {
		addInfoMessage(msg, "");
	}


	public static void addSuccessMessage(String msg) {
		addSuccessMessage(msg, "");
	}
	
	/**
	 * Actualmente se duplican los metodos hasta haber modificado todos los
	 * mensajes
	 */
	
	public static FacesMessage addErrorMessage(String etiqueta, String title, String msg) {
		log.error(msg);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, msg);
		FacesContext.getCurrentInstance().addMessage(etiqueta, facesMsg);

		return facesMsg;
	}


	public static void addSuccessMessage(String etiqueta, String title, String msg) {
		log.info(msg);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
		FacesContext.getCurrentInstance().addMessage(etiqueta, facesMsg);
	}


	public static void addInfoMessage(String etiqueta, String title, String msg) {
		log.warn(msg);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, title, msg);
		FacesContext.getCurrentInstance().addMessage(etiqueta, facesMsg);
	}


	public static boolean validarDNI(String dni) {
	    return Arrays.binarySearch(INVALIDOS, dni) < 0 // (1)
	        && REGEXP.matcher(dni).matches() // (2)
	        && dni.charAt(8) == DIGITO_CONTROL.charAt(Integer.parseInt(dni.substring(0, 8)) % 23); // (3)
	  }
	

}