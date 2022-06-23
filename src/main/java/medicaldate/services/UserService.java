package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import medicaldate.model.Paciente;
import medicaldate.model.User;
import medicaldate.repository.UserRepository;

@Service
public class UserService  {
	
	private UserRepository userRepository;
	
	public UserService(	@Autowired UserRepository userRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.userRepository = userRepository;
	}
	
	public List<User> getUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	public User getUserById(Long id) {
		return userRepository.findById(id).get();
	}
	
	public User obtenerUsuarioPorNombreUsuario(String userName) {
		return userRepository.obtenerUsuarioPorNombreUsuario(userName);
	}
	
	public List<User> getListaUsuariosPorNombre(){
		return  userRepository.obtenerListaUsuariosPorNombre();
	}
	
	public boolean existsUser(String formUserName) {
		return obtenerUsuarioPorNombreUsuario(formUserName)!=null;
	}
	public void saveUser(User user) {
		userRepository.save(user);		
	}
	
	public User getCurrentUser() {
		User result=null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();		    
		    result=userRepository.obtenerUsuarioPorNombreUsuario(currentUserName);
		}
		return result;
	}

}
