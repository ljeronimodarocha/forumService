package forumService.forumService.Service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import forumService.forumService.Models.Comentario;
import forumService.forumService.Repository.ComentarioRepository;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    public ResponseEntity<Comentario> resolucao(Long codigo) {
        Comentario comentarioSalvo = buscaPorCodigo(codigo);
        comentarioSalvo.setResolucao(!comentarioSalvo.isResolucao());
        comentarioRepository.save(comentarioSalvo);
        return ResponseEntity.ok(comentarioSalvo);
    }

    private Comentario buscaPorCodigo(Long codigo) {
        Comentario comentario = comentarioRepository.findById(codigo).orElse(null);
        if (comentario == null) {
            throw new EntityNotFoundException();
        }
        return comentario;
    }

    public Comentario findById(Long codigo) {
        return buscaPorCodigo(codigo);
    }

    public void deleteById(Long codigo) {
        Comentario comentario = buscaPorCodigo(codigo);
        comentarioRepository.save(comentario);
    }

}