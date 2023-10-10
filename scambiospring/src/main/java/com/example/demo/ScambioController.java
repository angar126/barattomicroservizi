package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/scambio")
public class ScambioController {
	private final ScambioService scambioService;

	@Autowired
	public ScambioController(ScambioService scambioService) {
		this.scambioService = scambioService;
	}

	@GetMapping
	public Scambio getUltimoScambio() {
		return scambioService.getUltimoScambio();
	}

	@PostMapping
	public void registerNewStudent(@RequestBody Scambio scambio) {
		scambioService.addNewScambio(scambio);
	}
	
}
	/*
	 * public List<Scambio> getScambios(){ return scambioService.getScambios(); }
	 */
	/*
	 * @DeleteMapping(path = "{scambioId}") public void
	 * deleteStudent(@PathVariable("scambioId")Long scambioId) {
	 * scambioService.deleteScambio(scambioId); }
	 */

