package forumService.forumService.Service;

import java.time.OffsetDateTime;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import forumService.forumService.Models.Comentario;
import forumService.forumService.Models.Post;
import forumService.forumService.Models.Status;
import forumService.forumService.Repository.ComentarioRepository;
import forumService.forumService.Repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public Post atualizar(Long codigo, @Valid Post post) {
        Post postSalvo = buscarPostPeloCodigo(codigo);
        BeanUtils.copyProperties(post, postSalvo, "codigo");
        return postRepository.save(postSalvo);
    }

    private Post buscarPostPeloCodigo(Long codigo) {
        Post postSalvo = postRepository.findById(codigo).orElse(null);
        if (postSalvo == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return postSalvo;
    }

    public Comentario adicionaComentario(Long codigo, @Valid Comentario comentario) {
        Post postSalvo = buscarPostPeloCodigo(codigo);
        comentario.setPost(postSalvo);
        comentario.setDataComentario(OffsetDateTime.now());
        comentario.setResolucao(false);
        return comentarioRepository.save(comentario);
    }

    public Post adicionar(Post post) {
        post.setDateCriacao(OffsetDateTime.now());
        post.setStatus(Status.ABERTO);
        return postRepository.save(post);
    }

    /*
     * private Date getLocalDateTimetoDate() { LocalDateTime localDateTime =
     * LocalDateTime.now(); Date date =
     * Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); return
     * date; }
     */

    public Post fechaPost(Long codigo) {
        Post post = buscarPostPeloCodigo(codigo);
        post.setStatus(Status.FECHADO);
        return postRepository.save(post);
    }

}
