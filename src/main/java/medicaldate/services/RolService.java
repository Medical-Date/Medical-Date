package medicaldate.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.model.Rol;
import medicaldate.repository.RolRepository;

@Service
public class RolService {
	
	private RolRepository rolRepository;

	public RolService(@Autowired RolRepository rolRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.rolRepository = rolRepository;

	}
	
	public List<Rol> getRoles(){
		return (List<Rol>) rolRepository.findAll();
	}
	
	public Rol obtenerRolPorNombre(String rol) {
		return rolRepository.obtenerRolPorNombre(rol);
	}
	
	public Rol obtenerRolAdmin() {
		return rolRepository.obtenerRolAdmin();
	}
	
	


}
