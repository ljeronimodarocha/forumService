package forumService.forumService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import forumService.forumService.Models.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}