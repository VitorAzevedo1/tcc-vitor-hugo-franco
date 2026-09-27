package br.com.pucgo.saudematuridade.consulta;

import br.com.pucgo.saudematuridade.consulta.dto.ConsultaRequest;
import br.com.pucgo.saudematuridade.consulta.dto.ConsultaResponse;
import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public ConsultaResponse criar(ConsultaRequest request) {
        Consulta consulta = new Consulta(
                null,
                request.dataHora(),
                request.medico(),
                request.especialidade(),
                request.local(),
                request.motivo(),
                request.diagnostico(),
                request.prescricao(),
                request.observacoes()
        );

        Consulta salva = repository.save(consulta);
        return ConsultaResponse.fromEntity(salva);
    }

    public List<ConsultaResponse> listar() {
        return repository.findAll().stream()
                .map(ConsultaResponse::fromEntity)
                .toList();
    }

    public ConsultaResponse buscarPorId(Long id) {
        Consulta consulta = buscarEntidadePorId(id);
        return ConsultaResponse.fromEntity(consulta);
    }

    public ConsultaResponse atualizar(Long id, ConsultaRequest request) {
        Consulta consulta = buscarEntidadePorId(id);

        consulta.setDataHora(request.dataHora());
        consulta.setMedico(request.medico());
        consulta.setEspecialidade(request.especialidade());
        consulta.setLocal(request.local());
        consulta.setMotivo(request.motivo());
        consulta.setDiagnostico(request.diagnostico());
        consulta.setPrescricao(request.prescricao());
        consulta.setObservacoes(request.observacoes());

        Consulta atualizada = repository.save(consulta);
        return ConsultaResponse.fromEntity(atualizada);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Consulta não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }

    private Consulta buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada com id: " + id));
    }
}