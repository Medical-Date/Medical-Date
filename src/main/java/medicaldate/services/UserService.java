package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	
	public User obtenerUsuarioPorNombreUsuario(String userName) {
		return userRepository.obtenerUsuarioPorNombreUsuario(userName);
	}

}
