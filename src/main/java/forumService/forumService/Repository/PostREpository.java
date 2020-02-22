package forumService.forumService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import forumService.forumService.Models.Post;

/**
 * PostREpository
 */
public interface PostREpository extends JpaRepository<Post, Long>{

    
}