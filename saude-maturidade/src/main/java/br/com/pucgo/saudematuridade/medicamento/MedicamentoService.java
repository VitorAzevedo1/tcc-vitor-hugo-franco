package br.com.pucgo.saudematuridade.medicamento;

import br.com.pucgo.saudematuridade.auth.UsuarioAutenticadoService;
import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import br.com.pucgo.saudematuridade.exception.RegraDeNegocioException;
import br.com.pucgo.saudematuridade.medicamento.dto.MedicamentoRequest;
import br.com.pucgo.saudematuridade.medicamento.dto.MedicamentoResponse;
import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MedicamentoService {

    private final MedicamentoRepository repository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public MedicamentoService(MedicamentoRepository repository,
                              UsuarioAutenticadoService usuarioAutenticadoService) {
        this.repository = repository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    public MedicamentoResponse criar(MedicamentoRequest request) {
        validarDatas(request.dataInicio(), request.dataFim());

        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();

        Medicamento medicamento = new Medicamento();
        medicamento.setUsuario(autenticado);
        medicamento.setNome(request.nome());
        medicamento.setDosagem(request.dosagem());
        medicamento.setFrequencia(request.frequencia());
        medicamento.setViaAdministracao(request.viaAdministracao());
        medicamento.setDataInicio(request.dataInicio());
        medicamento.setDataFim(request.dataFim());
        medicamento.setObservacoes(request.observacoes());

        Medicamento salvo = repository.save(medicamento);
        return MedicamentoResponse.fromEntity(salvo);
    }

    public List<MedicamentoResponse> listar() {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        return repository.findAllByUsuario(autenticado).stream()
                .map(MedicamentoResponse::fromEntity)
                .toList();
    }

    public MedicamentoResponse buscarPorId(Long id) {
        Medicamento medicamento = buscarEntidadePorIdDoUsuario(id);
        return MedicamentoResponse.fromEntity(medicamento);
    }

    public MedicamentoResponse atualizar(Long id, MedicamentoRequest request) {
        validarDatas(request.dataInicio(), request.dataFim());

        Medicamento medicamento = buscarEntidadePorIdDoUsuario(id);

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
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        if (!repository.existsByIdAndUsuario(id, autenticado)) {
            throw new RecursoNaoEncontradoException("Medicamento não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    private Medicamento buscarEntidadePorIdDoUsuario(Long id) {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        return repository.findByIdAndUsuario(id, autenticado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Medicamento não encontrado com id: " + id));
    }

    private void validarDatas(LocalDate dataInicio, LocalDate dataFim) {
        if (dataFim != null && dataFim.isBefore(dataInicio)) {
            throw new RegraDeNegocioException("A data de fim não pode ser anterior à data de início");
        }
    }
}