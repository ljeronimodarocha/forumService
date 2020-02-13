package forumService.forumService.Resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import forumService.forumService.Event.RecursoCriadoEvent;
import forumService.forumService.Models.Usuario;
import forumService.forumService.Repository.userRepository;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserResource {
	@Autowired
	userRepository repo;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping
	public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario u, HttpServletResponse response) {
		Usuario userSave = repo.save(u);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, userSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
	}

	@GetMapping
	public List<Usuario> get() {
		
		return repo.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Usuario> buscaPeloCodigo(@PathVariable Long codigo) {
		System.out.println(codigo);
		Usuario user = repo.findById(codigo).orElse(null);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
	}

}
