package forumService.forumService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import forumService.forumService.Models.Usuario;

public interface userRepository extends JpaRepository<Usuario, Long> {
	
}
