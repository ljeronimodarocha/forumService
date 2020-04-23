package forumService.forumService.Service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import forumService.forumService.Models.Estado;
import forumService.forumService.Models.Usuario;
import forumService.forumService.Repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario Atualizar(Long codigo, @Valid Usuario usuario) {
		Usuario usuarioNovo = buscarPostPeloCodigo(codigo);
		BeanUtils.copyProperties(usuario, usuarioNovo, "codigo");
		System.out.println(usuarioNovo.getEstado());
		return usuarioRepository.save(usuarioNovo);
	}

	private Usuario buscarPostPeloCodigo(Long codigo) {
		Usuario usuarioSalvo = usuarioRepository.findById(codigo).get();
		if (usuarioSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return usuarioSalvo;
	}

}
