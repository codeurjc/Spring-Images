package es.codeurjc.daw;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DatabaseInitializer {
	
	@Autowired
	private AnuncioRepository repository;
	
	@PostConstruct
	public void init() {
		
		this.repository.save(new Anuncio("Pepe", "Vendo moto", "Barata"));
		this.repository.save(new Anuncio("Juan", "Vendo coche", "Barato"));
	}

}
