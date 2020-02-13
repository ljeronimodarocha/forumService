package forumService.forumService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import forumService.forumService.Models.User;

public interface userRepository extends JpaRepository<User, Long> {
	
}
