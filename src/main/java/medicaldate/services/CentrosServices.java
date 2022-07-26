package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import medicaldate.model.Centros;
import medicaldate.model.User;
import medicaldate.repository.CentrosRepository;


@Service
public class CentrosServices {
	
	private CentrosRepository centrosRepository;

	public CentrosServices(@Autowired CentrosRepository centrosRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.centrosRepository = centrosRepository;

	}
	
	public List<Centros> getCentros(){
		return (List<Centros>) centrosRepository.findAll();
	}
	
	public Centros getCentrosById(Long id) {
		return centrosRepository.findById(id).get();
	}
	
	public List<Centros> getListaCentrosPorNombre() {
		return centrosRepository.obtenerListaCentrosPorNombre();
	}
	
	public Centros getCentrosPorNombre(String nombre) {
		return centrosRepository.obtenerCentrosPorNombre(nombre);
	}

}
