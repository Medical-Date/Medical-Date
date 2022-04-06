package medicaldate;

import java.util.Arrays;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MedicaldateApplication extends SpringBootServletInitializer{

//	public static void main(String[] args) {
//		SpringApplication.run(MedicaldateApplication.class, args);
//	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
         return application.sources(MedicaldateApplication .class);
    }

    public static void main(String[] args) {
         SpringApplication.run(MedicaldateApplication .class, args);
    }
	
//	 @Bean
//	    ServletRegistrationBean jsfServletRegistration (ServletContext servletContext) {
//	        //spring boot only works if this is set
//	        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
//
//	        //FacesServlet registration
//	        ServletRegistrationBean srb = new ServletRegistrationBean();
//	        srb.setServlet(new FacesServlet());
//	        srb.setUrlMappings(Arrays.asList("*.xhtml"));
//	        srb.setLoadOnStartup(1);
//	        return srb;
//	    }

}
