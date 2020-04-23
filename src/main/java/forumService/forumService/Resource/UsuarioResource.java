package forumService.forumService.Resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import forumService.forumService.Event.RecursoCriadoEvent;
import forumService.forumService.Models.Usuario;
import forumService.forumService.Repository.UsuarioRepository;
import forumService.forumService.Service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	@Autowired
	UsuarioRepository repo;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario u, HttpServletResponse response) {
		Usuario userSave = repo.save(u);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, userSave.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Usuario> get() {
		// EnumSet.allOf(Estado.class).forEach(day -> System.out.println(day));
		return repo.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Usuario> buscaPeloCodigo(@PathVariable Long codigo) {
		Usuario user = repo.findById(codigo).orElse(null);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long codigo, @Valid @RequestBody Usuario usuario) {
		Usuario usuarioSalvo = usuarioService.Atualizar(codigo, usuario);
		return ResponseEntity.ok(usuarioSalvo);
	}

	@DeleteMapping("/{codigo}")
	// @ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deletar(@PathVariable Long codigo) {
		if (!repo.findById(codigo).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		repo.deleteById(codigo);
		return ResponseEntity.noContent().build();
	}

}
