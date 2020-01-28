package forumService.forumService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import forumService.forumService.models.user;

public interface userRepository extends JpaRepository<user, Long> {
	@Query("select u from user u where u.email = ?1")
	user findByEmail(String email);
}
