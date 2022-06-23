package medicaldate;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import org.springframework.web.context.ServletContextAware;

import com.sun.faces.config.ConfigureListener;


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
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
	    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
	            new FacesServlet(), "*.xhtml");
	    servletRegistrationBean.setLoadOnStartup(1);
	    return servletRegistrationBean;
	}
	
	@Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<ConfigureListener>(
            new ConfigureListener());
    }
	

    public void setServletContext(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());           
    }

}
