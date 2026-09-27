package br.com.pucgo.saudematuridade.contatomedico;

import br.com.pucgo.saudematuridade.contatomedico.dto.ContatoMedicoRequest;
import br.com.pucgo.saudematuridade.contatomedico.dto.ContatoMedicoResponse;
import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContatoMedicoService {

    private final ContatoMedicoRepository repository;

    public ContatoMedicoService(ContatoMedicoRepository repository) {
        this.repository = repository;
    }

    public ContatoMedicoResponse criar(ContatoMedicoRequest request) {
        ContatoMedico contato = new ContatoMedico(
                null,
                request.nome(),
                request.especialidade(),
                request.telefone(),
                request.email(),
                request.crm(),
                request.localAtendimento(),
                request.endereco(),
                request.observacoes()
        );

        ContatoMedico salvo = repository.save(contato);
        return ContatoMedicoResponse.fromEntity(salvo);
    }

    public List<ContatoMedicoResponse> listar() {
        return repository.findAll().stream()
                .map(ContatoMedicoResponse::fromEntity)
                .toList();
    }

    public ContatoMedicoResponse buscarPorId(Long id) {
        ContatoMedico contato = buscarEntidadePorId(id);
        return ContatoMedicoResponse.fromEntity(contato);
    }

    public ContatoMedicoResponse atualizar(Long id, ContatoMedicoRequest request) {
        ContatoMedico contato = buscarEntidadePorId(id);

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
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Contato médico não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    private ContatoMedico buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Contato médico não encontrado com id: " + id));
    }
}