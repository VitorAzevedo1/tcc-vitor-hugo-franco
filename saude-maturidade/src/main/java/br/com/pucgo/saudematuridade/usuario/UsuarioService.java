package br.com.pucgo.saudematuridade.usuario;

import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import br.com.pucgo.saudematuridade.exception.RegraDeNegocioException;
import br.com.pucgo.saudematuridade.usuario.dto.CadastroUsuarioRequest;
import br.com.pucgo.saudematuridade.usuario.dto.UsuarioResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse cadastrar(CadastroUsuarioRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new RegraDeNegocioException("Já existe um usuário cadastrado com este e-mail");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenhaHash(passwordEncoder.encode(request.senha()));
        usuario.setPerfil(request.perfil());

        Usuario salvo = repository.save(usuario);
        return UsuarioResponse.fromEntity(salvo);
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com id: " + id));
        return UsuarioResponse.fromEntity(usuario);
    }
}
