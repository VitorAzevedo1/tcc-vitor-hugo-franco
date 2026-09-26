package br.com.pucgo.saudematuridade.medicamento;

import br.com.pucgo.saudematuridade.medicamento.dto.MedicamentoRequest;
import br.com.pucgo.saudematuridade.medicamento.dto.MedicamentoResponse;
import org.springframework.stereotype.Service;

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
        Medicamento medicamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado com id: " + id));
        return MedicamentoResponse.fromEntity(medicamento);
    }

    public MedicamentoResponse atualizar(Long id, MedicamentoRequest request) {
        validarDatas(request.dataInicio(), request.dataFim());

        Medicamento medicamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado com id: " + id));

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
            throw new RuntimeException("Medicamento não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    private void validarDatas(java.time.LocalDate dataInicio, java.time.LocalDate dataFim) {
        if (dataFim != null && dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início");
        }
    }
}
