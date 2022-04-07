package medicaldate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicaldateApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicaldateApplication.class, args);
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
