package forumService.forumService.Resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<Post> getMethodName() {
       return postRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Post> criar(@Valid @RequestBody Post post, HttpServletResponse response) {
        Post postSalvo = postService.adicionar(post);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, postSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(postSalvo);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Post> buscaPeloCodigo(@PathVariable Long codigo) {
        System.out.println(codigo);
        Post post = postRepository.findById(codigo).orElse(null);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
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

    @PostMapping("/{codigo}/comentarios")
    public ResponseEntity<Comentario> criarComentario(@PathVariable Long codigo,
            @Valid @RequestBody Comentario comentario, HttpServletResponse response) {
        Comentario comentarioSalvo = postService.adicionaComentario(codigo, comentario);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, comentarioSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioSalvo);
    }

  
}