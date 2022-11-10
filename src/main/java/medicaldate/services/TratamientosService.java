package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import medicaldate.model.Tratamientos;
import medicaldate.repository.TratamientosRepository;

@Service
public class TratamientosService {
	
private TratamientosRepository tratamientosRepository;
	
	public TratamientosService(	@Autowired TratamientosRepository tratamientosRepository) {
		super();
		// también lo guardo para mi por si quiero hacer consultas personalizadas.
		this.tratamientosRepository = tratamientosRepository;
	}
	
	public List<Tratamientos> getTratamientosByEnfermedad(Long id){
		return tratamientosRepository.getTratamientosByEnfermedad(id);
	}
	public Tratamientos getTratamientosById(Long id) {
		return tratamientosRepository.findById(id).get();
	}

}
