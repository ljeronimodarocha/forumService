package forumService.forumService.Service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import forumService.forumService.Models.Usuario;
import forumService.forumService.Repository.UsuarioRepository;

public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario Atualizar(Long codigo, @Valid Usuario usuario) {
		final Usuario usuarioSalvo = buscarPostPeloCodigo(codigo);
		BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo");
		return usuarioRepository.save(usuarioSalvo);
	}

	private Usuario buscarPostPeloCodigo(Long codigo) {
		final Usuario usuarioSalvo = usuarioRepository.getOne(codigo);
		if (usuarioSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return usuarioSalvo;
	}

}
