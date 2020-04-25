package forumService.forumService.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import forumService.forumService.Models.Usuario;
import forumService.forumService.Repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario Atualizar(Long codigo, @Valid Usuario usuario) {
		Usuario usuarioNovo = buscarUsuarioPeloCodigo(codigo);
		BeanUtils.copyProperties(usuario, usuarioNovo, "codigo");
		System.out.println(usuarioNovo.getEstado());
		return usuarioRepository.save(usuarioNovo);
	}

	private Usuario buscarUsuarioPeloCodigo(Long codigo) {
		Usuario usuarioSalvo = usuarioRepository.findById(codigo).orElse(null);
		if (usuarioSalvo == null) {
			throw new EntityNotFoundException();
		}
		return usuarioSalvo;
	}

	public Usuario save(@Valid Usuario u) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String passwordEndocer = encoder.encode(u.getPassword());
		u.setPassword(passwordEndocer);
		return usuarioRepository.save(u);
	}

}
