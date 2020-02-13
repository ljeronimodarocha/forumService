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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import forumService.forumService.Event.RecursoCriadoEvent;
import forumService.forumService.Models.User;
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
	public ResponseEntity<User> criar(@Valid @RequestBody User u, HttpServletResponse response) {
		User userSave = repo.save(u);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, userSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
	}

	@RequestMapping(method = RequestMethod.GET, value = "get")
	public List<User> get() {

		return repo.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<User> buscaPeloCodigo(@PathVariable Long codigo) {
		User user = repo.findById(codigo).orElse(null);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
	}

}
