package br.com.pucgo.saudematuridade.observacaopessoal;

import br.com.pucgo.saudematuridade.auth.UsuarioAutenticadoService;
import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import br.com.pucgo.saudematuridade.observacaopessoal.dto.ObservacaoPessoalRequest;
import br.com.pucgo.saudematuridade.observacaopessoal.dto.ObservacaoPessoalResponse;
import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObservacaoPessoalService {

    private final ObservacaoPessoalRepository repository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public ObservacaoPessoalService(ObservacaoPessoalRepository repository,
                                    UsuarioAutenticadoService usuarioAutenticadoService) {
        this.repository = repository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    public ObservacaoPessoalResponse criar(ObservacaoPessoalRequest request) {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();

        ObservacaoPessoal observacao = new ObservacaoPessoal();
        observacao.setUsuario(autenticado);
        observacao.setTitulo(request.titulo());
        observacao.setDescricao(request.descricao());
        observacao.setDataHora(request.dataHora());
        observacao.setCategoria(request.categoria());

        ObservacaoPessoal salva = repository.save(observacao);
        return ObservacaoPessoalResponse.fromEntity(salva);
    }

    public List<ObservacaoPessoalResponse> listar() {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        return repository.findAllByUsuario(autenticado).stream()
                .map(ObservacaoPessoalResponse::fromEntity)
                .toList();
    }

    public ObservacaoPessoalResponse buscarPorId(Long id) {
        ObservacaoPessoal observacao = buscarEntidadePorIdDoUsuario(id);
        return ObservacaoPessoalResponse.fromEntity(observacao);
    }

    public ObservacaoPessoalResponse atualizar(Long id, ObservacaoPessoalRequest request) {
        ObservacaoPessoal observacao = buscarEntidadePorIdDoUsuario(id);

        observacao.setTitulo(request.titulo());
        observacao.setDescricao(request.descricao());
        observacao.setDataHora(request.dataHora());
        observacao.setCategoria(request.categoria());

        ObservacaoPessoal atualizada = repository.save(observacao);
        return ObservacaoPessoalResponse.fromEntity(atualizada);
    }

    public void deletar(Long id) {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        if (!repository.existsByIdAndUsuario(id, autenticado)) {
            throw new RecursoNaoEncontradoException("Observação não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }

    private ObservacaoPessoal buscarEntidadePorIdDoUsuario(Long id) {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        return repository.findByIdAndUsuario(id, autenticado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Observação não encontrada com id: " + id));
    }
}