package forumService.forumService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import forumService.forumService.Models.Post;

/**
 * PostREpository
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    
}