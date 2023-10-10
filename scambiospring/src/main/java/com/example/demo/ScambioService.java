package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ScambioService {
	private final ScambioRepository scambioRepository;

	@Autowired
	public ScambioService(com.example.demo.ScambioRepository scambioRepository) {
		this.scambioRepository = scambioRepository;
	}

	public List<Scambio> getScambios() {
		return scambioRepository.findAll();
	}
	public Scambio getUltimoScambio() {
		List<Scambio> lista = getScambios();
		if (lista.isEmpty())
			return null;
		return lista.get(lista.size() - 1);
	}

	public void addNewScambio(Scambio scambio) {
		scambioRepository.save(scambio);
	}

	public void deleteScambio(Long scambioId) {
		scambioRepository.findById(scambioId);
		boolean exists = scambioRepository.existsById(scambioId);
		if (!exists) {
			throw new IllegalStateException("scambio with id " + scambioId + " does not exists");
		}
		scambioRepository.deleteById(scambioId);
	}

}
//addnewscambio
/**Optional<Scambio> scambioOptional = scambioRepository.findScambioByEmail(student.getEmail());
if (studentOptional.isPresent()) {
	throw new IllegalStateException("email taken");
}**/
