package br.com.pucgo.saudematuridade.alergia;

import br.com.pucgo.saudematuridade.alergia.dto.AlergiaRequest;
import br.com.pucgo.saudematuridade.alergia.dto.AlergiaResponse;
import br.com.pucgo.saudematuridade.auth.UsuarioAutenticadoService;
import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlergiaService {

    private final AlergiaRepository repository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public AlergiaService(AlergiaRepository repository,
                          UsuarioAutenticadoService usuarioAutenticadoService) {
        this.repository = repository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    public AlergiaResponse criar(AlergiaRequest request) {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();

        Alergia alergia = new Alergia();
        alergia.setUsuario(autenticado);
        alergia.setSubstancia(request.substancia());
        alergia.setTipo(request.tipo());
        alergia.setGravidade(request.gravidade());
        alergia.setReacao(request.reacao());
        alergia.setDataDescoberta(request.dataDescoberta());
        alergia.setObservacoes(request.observacoes());

        Alergia salva = repository.save(alergia);
        return AlergiaResponse.fromEntity(salva);
    }

    public List<AlergiaResponse> listar() {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        return repository.findAllByUsuario(autenticado).stream()
                .map(AlergiaResponse::fromEntity)
                .toList();
    }

    public AlergiaResponse buscarPorId(Long id) {
        Alergia alergia = buscarEntidadePorIdDoUsuario(id);
        return AlergiaResponse.fromEntity(alergia);
    }

    public AlergiaResponse atualizar(Long id, AlergiaRequest request) {
        Alergia alergia = buscarEntidadePorIdDoUsuario(id);

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
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        if (!repository.existsByIdAndUsuario(id, autenticado)) {
            throw new RecursoNaoEncontradoException("Alergia não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }

    private Alergia buscarEntidadePorIdDoUsuario(Long id) {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();
        return repository.findByIdAndUsuario(id, autenticado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Alergia não encontrada com id: " + id));
    }
}