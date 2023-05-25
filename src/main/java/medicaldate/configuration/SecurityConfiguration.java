package medicaldate.configuration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	        .antMatchers("/listCitas.xhtml").hasAnyAuthority("ADMINISTRADOR","PACIENTE","MEDICO")
	        .antMatchers("/asignacionCalendario.xhtml").hasAnyAuthority("ADMINISTRADOR")
	        .antMatchers("/listaHistoriales.xhtml").hasAnyAuthority("ADMINISTRADOR","MEDICO")
	        .antMatchers("/historialRegister.xhtml").hasAnyAuthority("ADMINISTRADOR","MEDICO")
	        .antMatchers("/enfermedadRegister.xhtml").hasAnyAuthority("ADMINISTRADOR")
	        .antMatchers("/crearTratamientos.xhtml").hasAnyAuthority("ADMINISTRADOR")
	        .antMatchers("/listaEnfermedades.xhtml").hasAnyAuthority("ADMINISTRADOR","MEDICO","PACIENTE")
	        .antMatchers("/crearCentro.xhtml").hasAnyAuthority("ADMINISTRADOR")
	        .antMatchers("/listCentros.xhtml").hasAnyAuthority("ADMINISTRADOR")
	        .antMatchers("/asignarMedicoCentro.xhtml").hasAnyAuthority("ADMINISTRADOR")
	        .antMatchers("/listaMedicosCentrosAsignados.xhtml").hasAnyAuthority("ADMINISTRADOR")
	        .antMatchers("/listaSolicitudesCambioMedico.xhtml").hasAnyAuthority("ADMINISTRADOR")
	        .antMatchers("/listaSolicitudes.xhtml").hasAnyAuthority("ADMINISTRADOR")
	        .antMatchers("/listaSolicitudesCambioCentro.xhtml").hasAnyAuthority("ADMINISTRADOR")
	        .antMatchers("/solicitudCambioMedico.xhtml").hasAnyAuthority("ADMINISTRADOR","PACIENTE")
	        .antMatchers("/solicitudCambioCentro.xhtml").hasAnyAuthority("ADMINISTRADOR","MEDICO")
	        .and()
	            .formLogin()
	            .loginPage("/login")
	            .failureUrl("/login?error")
	        .and()
	            .logout()
	            .logoutUrl("/index.xhtml")
	            .logoutSuccessUrl("/")
	        .and()
	            .csrf().disable()
	            .headers().frameOptions().sameOrigin();
	}
//	private String[] getAuthorities(String... roles) {
//	    try (Connection connection = dataSource.getConnection()) {
//	    	String rolesQuery = "SELECT rol FROM RolUsuarios WHERE rol IN ('" + String.join("','", roles) + "')";
//	        try (Statement statement = connection.createStatement()) {
//	            ResultSet resultSet = statement.executeQuery(rolesQuery);
//	            List<String> authorities = new ArrayList<>();
//	            while (resultSet.next()) {
//	                authorities.add(resultSet.getString("rol"));
//	            }
//	            return authorities.toArray(new String[0]);
//	        }
//	    } catch (SQLException e) {
//	        // Manejar la excepción adecuadamente
//	    }
//	    return new String[0];
//	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.jdbcAuthentication()
	        .dataSource(dataSource)
	        .usersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username = ?")
	        .authoritiesByUsernameQuery("SELECT username, rol FROM rolusuarios WHERE username = ?")
	        .passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
}