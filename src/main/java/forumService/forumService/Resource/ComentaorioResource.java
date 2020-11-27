package forumService.forumService.Resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import forumService.forumService.Models.Comentario;
import forumService.forumService.Repository.ComentarioRepository;
import forumService.forumService.Service.ComentarioService;

@RestController
@RequestMapping("/comentarios")
public class ComentaorioResource {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    private List<Comentario> getAll() {
        return comentarioRepository.findAll();
    }

    @GetMapping("/{codigo}")
    private ResponseEntity<Comentario> findById(@PathVariable Long codigo) {
        Comentario comentario = comentarioService.findById(codigo);
        return ResponseEntity.ok(comentario);
    }

    @GetMapping("/{codigo}/resolucao")
    private ResponseEntity<Comentario> isResolution(@PathVariable Long codigo) {
        return comentarioService.resolucao(codigo);
    }

    @DeleteMapping("/{codigo}")
    private ResponseEntity<Void> deleta(@PathVariable Long codigo) {
        comentarioService.deleteById(codigo);
        return ResponseEntity.noContent().build();
    }

}