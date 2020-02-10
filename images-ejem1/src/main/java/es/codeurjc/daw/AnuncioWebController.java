package es.codeurjc.daw;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AnuncioWebController {

	@Autowired
	private AnuncioRepository repository;
	
	@Autowired
	private ImageService imgService;

	@GetMapping("/")
	public String tablon(Model model) {

		List<Anuncio> anuncios = repository.findAll();
		
		model.addAttribute("anuncios", anuncios);

		return "tablon";
	}
	
	@GetMapping("/anuncio/nuevo")
	public String nuevoAnuncioForm() {
		return "nuevo_anuncio";
	}

	@PostMapping("/anuncio/guardar")
	public String nuevoAnuncio(Model model, Anuncio anuncio, @RequestParam MultipartFile imagenFile) throws IOException {

		anuncio.setImagen(true);
		
		repository.save(anuncio);
		
		imgService.saveImage("anuncios", anuncio.getId(), imagenFile);

		return "anuncio_guardado";

	}

	@GetMapping("/anuncio/{id}")
	public String verAnuncio(Model model, @PathVariable long id) {

		Optional<Anuncio> anuncio = repository.findById(id);
		if(anuncio.isPresent()) {
			model.addAttribute("anuncio", anuncio.get());
		}
		
		return "ver_anuncio";
	}
}