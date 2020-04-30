package forumService.forumService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import forumService.forumService.Models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);
	
}
