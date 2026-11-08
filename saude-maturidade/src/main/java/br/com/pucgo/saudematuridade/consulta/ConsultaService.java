package br.com.pucgo.saudematuridade.consulta;

import br.com.pucgo.saudematuridade.auth.UsuarioAutenticadoService;
import br.com.pucgo.saudematuridade.consulta.dto.ConsultaRequest;
import br.com.pucgo.saudematuridade.consulta.dto.ConsultaResponse;
import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public ConsultaService(ConsultaRepository repository,
                           UsuarioAutenticadoService usuarioAutenticadoService) {
        this.repository = repository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    public ConsultaResponse criar(ConsultaRequest request) {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();

        Consulta consulta = new Consulta();
        consulta.setUsuario(autenticado);
        consulta.setDataHora(request.dataHora());
        consulta.setMedico(request.medico());
        consulta.setEspecialidade(request.especialidade());
        consulta.setLocal(request.local());
        consulta.setMotivo(request.motivo());
        consulta.setDiagnostico(request.diagnostico());
        consulta.setPrescricao(request.prescricao());
        consulta.setObservacoes(request.observacoes());

        Consulta salva = repository.save(consulta);
        return ConsultaResponse.fromEntity(salva);
    }

    public List<ConsultaResponse> listar() {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        return repository.findAllByUsuario(autenticado).stream()
                .map(ConsultaResponse::fromEntity)
                .toList();
    }

    public ConsultaResponse buscarPorId(Long id) {
        Consulta consulta = buscarEntidadePorIdDoUsuario(id);
        return ConsultaResponse.fromEntity(consulta);
    }

    public ConsultaResponse atualizar(Long id, ConsultaRequest request) {
        Consulta consulta = buscarEntidadePorIdDoUsuario(id);

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
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        if (!repository.existsByIdAndUsuario(id, autenticado)) {
            throw new RecursoNaoEncontradoException("Consulta não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }

    private Consulta buscarEntidadePorIdDoUsuario(Long id) {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        return repository.findByIdAndUsuario(id, autenticado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada com id: " + id));
    }
}