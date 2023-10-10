package Models;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MicorserviceScambio {

	String url = "http://scambiomicroservizio:8080/api/scambio";//"http://localhost:8080/api/scambio";
	private final RestTemplate restTemplate;

	public MicorserviceScambio() {
		this.restTemplate = new RestTemplate();
	}

	public Scambio getUltimoMicroserviceScambio() {
		// Utilizza il RestTemplate per effettuare una chiamata al microservizio
		Scambio scambio = restTemplate.getForObject(url, Scambio.class);
		return scambio;

	}

	public void salvaMicroserviceScambio(Scambio scambioToAdd) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("piazza", scambioToAdd.getPiazza());
		requestBody.put("luogo", scambioToAdd.getLuogo());
		requestBody.put("giorni", scambioToAdd.getGiorni());
		requestBody.put("orari", scambioToAdd.getOrari());
		requestBody.put("scadenza", scambioToAdd.getScadenzaString());

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
		restTemplate.postForEntity(url, requestEntity, Scambio.class);
	}
}
	// String url= "http://localhost:8080/api/scambio";
	// JSONObject obj = new JSONObject(jsonString);
	// int ultimoId = obj.getInt("id");

	// Esegui l'operazione desiderata con la risposta ottenuta dal microservizio
	// Scambio scambio = new Scambio(obj);
	// System.out.println(scambio.toString());
	/*
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_JSON);
	 * 
	 * Map<String, String> requestBody = new HashMap<>(); requestBody.put("title",
	 * "Hello World"); requestBody.put("content", "Lorem ipsum dolor sit amet");
	 * 
	 * HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody,
	 * headers);
	 * 
	 * RestTemplate restTemplate = new RestTemplate();
	 * 
	 * ResponseEntity<Post> responseEntity = restTemplate.postForEntity(url,
	 * requestEntity, Post.class); Post post = responseEntity.getBody();
	 */