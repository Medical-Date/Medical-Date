package medicaldate.services;

import java.util.List;

import org.springframework.stereotype.Service;

import medicaldate.model.User;
import medicaldate.repository.UserRepository;

@Service
public class UserService  {
	
	private UserRepository userRepository;
	
	public List<User> getUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	public User obtenerUsuarioPorNombreUsuario(String userName) {
		return userRepository.obtenerUsuarioPorNombreUsuario(userName);
	}

}
