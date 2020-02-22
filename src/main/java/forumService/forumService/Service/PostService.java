package forumService.forumService.Service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import forumService.forumService.Models.Post;
import forumService.forumService.Repository.PostREpository;

public class PostService {

    @Autowired
    private PostREpository postRepository;

    public Post Atualizar(Long codigo, @Valid Post post) {
        final Post postSalvo = buscarPostPeloCodigo(codigo);
        BeanUtils.copyProperties(post, postSalvo, "codigo");
        return postRepository.save(postSalvo);
    }

    private Post buscarPostPeloCodigo(Long codigo) {
        final Post postSalvo = postRepository.getOne(codigo);
        if (postSalvo == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return postSalvo;
    }

}
