package medicaldate.configuration;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class CustomErrorController implements ErrorController {
	
	private static final String PATH="/error";
	
	@RequestMapping(value=PATH)
	public String error() {
		return "error.xhtml";
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
