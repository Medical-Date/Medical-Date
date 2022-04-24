package medicaldate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medicaldate.model.Historial;
import medicaldate.repository.HistorialRepository;

@Service
public class HistorialService {

	private HistorialRepository historialRepository;

	public HistorialService(@Autowired HistorialRepository historialRepository) {
		super();
		this.historialRepository = historialRepository;
	}

	public List<Historial> getHistoriales() {
		return (List<Historial>) historialRepository.findAll();
	}

	public Historial getHistorialById(Long id) {
		return historialRepository.findById(id).get();
	}

}
