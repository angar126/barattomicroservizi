package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ScambioConfig {
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
/*@Bean
CommandLineRunner comandLineRunner(ScambioRepository repository) {
	return args -> {
		Scambio prova = new Scambio("Piazza","via fasulla","Lunedi","10:00-11:00",10
				);
		
		repository.saveAll(List.of(prova));
	};
}*/