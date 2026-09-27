package br.com.pucgo.saudematuridade.alergia;

import br.com.pucgo.saudematuridade.alergia.dto.AlergiaRequest;
import br.com.pucgo.saudematuridade.alergia.dto.AlergiaResponse;
import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlergiaService {

    private final AlergiaRepository repository;

    public AlergiaService(AlergiaRepository repository) {
        this.repository = repository;
    }

    public AlergiaResponse criar(AlergiaRequest request) {
        Alergia alergia = new Alergia(
                null,
                request.substancia(),
                request.tipo(),
                request.gravidade(),
                request.reacao(),
                request.dataDescoberta(),
                request.observacoes()
        );

        Alergia salva = repository.save(alergia);
        return AlergiaResponse.fromEntity(salva);
    }

    public List<AlergiaResponse> listar() {
        return repository.findAll().stream()
                .map(AlergiaResponse::fromEntity)
                .toList();
    }

    public AlergiaResponse buscarPorId(Long id) {
        Alergia alergia = buscarEntidadePorId(id);
        return AlergiaResponse.fromEntity(alergia);
    }

    public AlergiaResponse atualizar(Long id, AlergiaRequest request) {
        Alergia alergia = buscarEntidadePorId(id);

        alergia.setSubstancia(request.substancia());
        alergia.setTipo(request.tipo());
        alergia.setGravidade(request.gravidade());
        alergia.setReacao(request.reacao());
        alergia.setDataDescoberta(request.dataDescoberta());
        alergia.setObservacoes(request.observacoes());

        Alergia atualizada = repository.save(alergia);
        return AlergiaResponse.fromEntity(atualizada);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Alergia não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }

    private Alergia buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Alergia não encontrada com id: " + id));
    }
}
