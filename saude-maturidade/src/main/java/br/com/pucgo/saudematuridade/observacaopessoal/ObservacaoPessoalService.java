package br.com.pucgo.saudematuridade.observacaopessoal;

import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import br.com.pucgo.saudematuridade.observacaopessoal.dto.ObservacaoPessoalRequest;
import br.com.pucgo.saudematuridade.observacaopessoal.dto.ObservacaoPessoalResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObservacaoPessoalService {

    private final ObservacaoPessoalRepository repository;

    public ObservacaoPessoalService(ObservacaoPessoalRepository repository) {
        this.repository = repository;
    }

    public ObservacaoPessoalResponse criar(ObservacaoPessoalRequest request) {
        ObservacaoPessoal observacao = new ObservacaoPessoal(
                null,
                request.titulo(),
                request.descricao(),
                request.dataHora(),
                request.categoria()
        );

        ObservacaoPessoal salva = repository.save(observacao);
        return ObservacaoPessoalResponse.fromEntity(salva);
    }

    public List<ObservacaoPessoalResponse> listar() {
        return repository.findAll().stream()
                .map(ObservacaoPessoalResponse::fromEntity)
                .toList();
    }

    public ObservacaoPessoalResponse buscarPorId(Long id) {
        ObservacaoPessoal observacao = buscarEntidadePorId(id);
        return ObservacaoPessoalResponse.fromEntity(observacao);
    }

    public ObservacaoPessoalResponse atualizar(Long id, ObservacaoPessoalRequest request) {
        ObservacaoPessoal observacao = buscarEntidadePorId(id);

        observacao.setTitulo(request.titulo());
        observacao.setDescricao(request.descricao());
        observacao.setDataHora(request.dataHora());
        observacao.setCategoria(request.categoria());

        ObservacaoPessoal atualizada = repository.save(observacao);
        return ObservacaoPessoalResponse.fromEntity(atualizada);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Observação não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }

    private ObservacaoPessoal buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Observação não encontrada com id: " + id));
    }
}
