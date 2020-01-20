package forumService.forumService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import forumService.forumService.models.user;

public interface userRepository extends JpaRepository<user, Long> {

}
