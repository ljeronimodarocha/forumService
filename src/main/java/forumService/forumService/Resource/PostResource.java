package forumService.forumService.Resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import forumService.forumService.Models.Comentario;
import forumService.forumService.Models.Post;
import forumService.forumService.Repository.PostRepository;
import forumService.forumService.Service.PostService;

/**
 * PostResource
 */
@RestController
@RequestMapping("/posts")
public class PostResource {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Post> buscaPeloCodigo(@PathVariable Long codigo) {
        Post post = postRepository.findById(codigo).orElse(null);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{codigo}/fecha")
    public ResponseEntity<Post> fechaPost(@PathVariable Long codigo) {
        Post postSalvo = postService.fechaPost(codigo);
        return ResponseEntity.ok(postSalvo);
    }

    @PostMapping
    public ResponseEntity<Post> criar(@Valid @RequestBody Post post, HttpServletResponse response) {
        Post postSalvo = postService.adicionar(post);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, postSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(postSalvo);
    }

    @PostMapping("/{codigo}/comentarios")
    public ResponseEntity<Comentario> criarComentario(@PathVariable Long codigo,
            @Valid @RequestBody Comentario comentario, HttpServletResponse response) {
        Comentario comentarioSalvo = postService.adicionaComentario(codigo, comentario);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, comentarioSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioSalvo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Post> atualizar(@PathVariable final Long codigo, @Valid @RequestBody final Post post) {
        Post postSalva = postService.atualizar(codigo, post);
        return ResponseEntity.ok(postSalva);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long codigo) {
        postRepository.deleteById(codigo);
    }

}