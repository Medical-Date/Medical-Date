package medicaldate.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import medicaldate.model.Rol;
import medicaldate.services.RolService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/listMedicos.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/listaSolicitudesCambioMedico.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/listUsers.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/asignarMedico.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/listaMedicosPacienteCentro.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/listaSolicitudes.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/listPacientes.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/asignacionCalendario.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/listCitas.xhtml").hasAnyAuthority("PACIENTE","MEDICO")
		.antMatchers("/asignacionCalendario.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/listaHistoriales.xhtml").hasAnyAuthority("MEDICO")
		.antMatchers("/historialRegister.xhtml").hasAnyAuthority("MEDICO")
		.antMatchers("/enfermedadRegister.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/crearTratamientos.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/listaEnfermedades.xhtml").hasAnyAuthority("ADMINISTRADOR","MEDICO")
		.antMatchers("/crearCentro.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/listCentros.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/asignarMedicoCentro.xhtml").hasAnyAuthority("ADMINISTRADOR")
		.antMatchers("/listaMedicosCentrosAsignados.xhtml").hasAnyAuthority("ADMINISTRADOR")
				.and()
				 	.formLogin()
				 	.loginPage("/login")
				 	.failureUrl("/login-error")
				.and()
					.logout()
					.logoutUrl("/index.xhtml")
						.logoutSuccessUrl("/"); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().disable();
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select username,password,enabled "
	        + "from user "
	        + "where username = ?")
	      .authoritiesByUsernameQuery("select username, rol from rolUsuarios where username=?")   	      
	      .passwordEncoder(passwordEncoder());	
	}

	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
}