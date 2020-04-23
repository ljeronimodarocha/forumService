package forumService.forumService.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import forumService.forumService.Models.Comentario;
import forumService.forumService.Models.Post;
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
        return comentarioRepository.save(comentario);
    }

    public Post adicionar(Post post) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        post.setDateCriacao(date);
        post.setStatusAberto(true);
        return postRepository.save(post);
    }

}
