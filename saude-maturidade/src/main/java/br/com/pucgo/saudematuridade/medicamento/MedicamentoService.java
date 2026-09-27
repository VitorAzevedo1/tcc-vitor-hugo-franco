package br.com.pucgo.saudematuridade.medicamento;

import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import br.com.pucgo.saudematuridade.exception.RegraDeNegocioException;
import br.com.pucgo.saudematuridade.medicamento.dto.MedicamentoRequest;
import br.com.pucgo.saudematuridade.medicamento.dto.MedicamentoResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MedicamentoService {

    private final MedicamentoRepository repository;

    public MedicamentoService(MedicamentoRepository repository) {
        this.repository = repository;
    }

    public MedicamentoResponse criar(MedicamentoRequest request) {
        validarDatas(request.dataInicio(), request.dataFim());

        Medicamento medicamento = new Medicamento(
                null,
                request.nome(),
                request.dosagem(),
                request.frequencia(),
                request.viaAdministracao(),
                request.dataInicio(),
                request.dataFim(),
                request.observacoes()
        );

        Medicamento salvo = repository.save(medicamento);
        return MedicamentoResponse.fromEntity(salvo);
    }

    public List<MedicamentoResponse> listar() {
        return repository.findAll().stream()
                .map(MedicamentoResponse::fromEntity)
                .toList();
    }

    public MedicamentoResponse buscarPorId(Long id) {
        Medicamento medicamento = buscarEntidadePorId(id);
        return MedicamentoResponse.fromEntity(medicamento);
    }

    public MedicamentoResponse atualizar(Long id, MedicamentoRequest request) {
        validarDatas(request.dataInicio(), request.dataFim());

        Medicamento medicamento = buscarEntidadePorId(id);

        medicamento.setNome(request.nome());
        medicamento.setDosagem(request.dosagem());
        medicamento.setFrequencia(request.frequencia());
        medicamento.setViaAdministracao(request.viaAdministracao());
        medicamento.setDataInicio(request.dataInicio());
        medicamento.setDataFim(request.dataFim());
        medicamento.setObservacoes(request.observacoes());

        Medicamento atualizado = repository.save(medicamento);
        return MedicamentoResponse.fromEntity(atualizado);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Medicamento não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    private Medicamento buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Medicamento não encontrado com id: " + id));
    }

    private void validarDatas(LocalDate dataInicio, LocalDate dataFim) {
        if (dataFim != null && dataFim.isBefore(dataInicio)) {
            throw new RegraDeNegocioException("A data de fim não pode ser anterior à data de início");
        }
    }
}