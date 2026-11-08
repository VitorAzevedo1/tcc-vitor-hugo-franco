package br.com.pucgo.saudematuridade.contatomedico;

import br.com.pucgo.saudematuridade.auth.UsuarioAutenticadoService;
import br.com.pucgo.saudematuridade.contatomedico.dto.ContatoMedicoRequest;
import br.com.pucgo.saudematuridade.contatomedico.dto.ContatoMedicoResponse;
import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContatoMedicoService {

    private final ContatoMedicoRepository repository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public ContatoMedicoService(ContatoMedicoRepository repository,
                                UsuarioAutenticadoService usuarioAutenticadoService) {
        this.repository = repository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    public ContatoMedicoResponse criar(ContatoMedicoRequest request) {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();

        ContatoMedico contato = new ContatoMedico();
        contato.setUsuario(autenticado);
        contato.setNome(request.nome());
        contato.setEspecialidade(request.especialidade());
        contato.setTelefone(request.telefone());
        contato.setEmail(request.email());
        contato.setCrm(request.crm());
        contato.setLocalAtendimento(request.localAtendimento());
        contato.setEndereco(request.endereco());
        contato.setObservacoes(request.observacoes());

        ContatoMedico salvo = repository.save(contato);
        return ContatoMedicoResponse.fromEntity(salvo);
    }

    public List<ContatoMedicoResponse> listar() {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        return repository.findAllByUsuario(autenticado).stream()
                .map(ContatoMedicoResponse::fromEntity)
                .toList();
    }

    public ContatoMedicoResponse buscarPorId(Long id) {
        ContatoMedico contato = buscarEntidadePorIdDoUsuario(id);
        return ContatoMedicoResponse.fromEntity(contato);
    }

    public ContatoMedicoResponse atualizar(Long id, ContatoMedicoRequest request) {
        ContatoMedico contato = buscarEntidadePorIdDoUsuario(id);

        contato.setNome(request.nome());
        contato.setEspecialidade(request.especialidade());
        contato.setTelefone(request.telefone());
        contato.setEmail(request.email());
        contato.setCrm(request.crm());
        contato.setLocalAtendimento(request.localAtendimento());
        contato.setEndereco(request.endereco());
        contato.setObservacoes(request.observacoes());

        ContatoMedico atualizado = repository.save(contato);
        return ContatoMedicoResponse.fromEntity(atualizado);
    }

    public void deletar(Long id) {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        if (!repository.existsByIdAndUsuario(id, autenticado)) {
            throw new RecursoNaoEncontradoException("Contato médico não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    private ContatoMedico buscarEntidadePorIdDoUsuario(Long id) {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        return repository.findByIdAndUsuario(id, autenticado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Contato médico não encontrado com id: " + id));
    }
}