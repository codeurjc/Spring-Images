package es.codeurjc.daw;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/anuncios")
public class AnuncioRestController {

	@Autowired
	private AnuncioRepository repository;

	@Autowired
	private ImageService imgService;

	@GetMapping("/")
	public List<Anuncio> tablon() {
		return repository.findAll();
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Anuncio nuevoAnuncio(@RequestBody Anuncio anuncio) {

		repository.save(anuncio);

		return anuncio;
	}

	@PostMapping("/{id}/imagen")
	public ResponseEntity<Anuncio> nuevaImagenAnuncio(@PathVariable long id, @RequestParam MultipartFile imagenFile)
			throws IOException {

		Optional<Anuncio> anuncio = repository.findById(id);

		if (anuncio.isPresent()) {

			anuncio.get().setImagen(true);
			repository.save(anuncio.get());

			imgService.saveImage("anuncios", anuncio.get().getId(), imagenFile);
			return new ResponseEntity<>(HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}/imagen")
	public ResponseEntity<Object> getImagenAnuncio(@PathVariable long id) throws IOException {

		Optional<Anuncio> anuncio = repository.findById(id);

		if (anuncio.isPresent()) {

			if(anuncio.get().hasImagen()) {
	
				return this.imgService.createResponseFromImage("anuncios", id);
				
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
	
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Anuncio> verAnuncio(@PathVariable long id) {

		Optional<Anuncio> anuncio = repository.findById(id);
		if (anuncio.isPresent()) {
			return new ResponseEntity<>(anuncio.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}