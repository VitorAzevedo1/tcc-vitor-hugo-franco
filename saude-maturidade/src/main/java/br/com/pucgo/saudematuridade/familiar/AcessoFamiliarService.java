package br.com.pucgo.saudematuridade.familiar;

import br.com.pucgo.saudematuridade.alergia.AlergiaRepository;
import br.com.pucgo.saudematuridade.alergia.dto.AlergiaResponse;
import br.com.pucgo.saudematuridade.auth.UsuarioAutenticadoService;
import br.com.pucgo.saudematuridade.consulta.ConsultaRepository;
import br.com.pucgo.saudematuridade.consulta.dto.ConsultaResponse;
import br.com.pucgo.saudematuridade.contatomedico.ContatoMedicoRepository;
import br.com.pucgo.saudematuridade.contatomedico.dto.ContatoMedicoResponse;
import br.com.pucgo.saudematuridade.exception.AcessoNegadoException;
import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import br.com.pucgo.saudematuridade.medicamento.MedicamentoRepository;
import br.com.pucgo.saudematuridade.medicamento.dto.MedicamentoResponse;
import br.com.pucgo.saudematuridade.observacaopessoal.ObservacaoPessoalRepository;
import br.com.pucgo.saudematuridade.observacaopessoal.dto.ObservacaoPessoalResponse;
import br.com.pucgo.saudematuridade.usuario.PerfilUsuario;
import br.com.pucgo.saudematuridade.usuario.Usuario;
import br.com.pucgo.saudematuridade.usuario.UsuarioRepository;
import br.com.pucgo.saudematuridade.vinculo.VinculoRepository;
import br.com.pucgo.saudematuridade.vinculo.VinculoStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcessoFamiliarService {

    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final UsuarioRepository usuarioRepository;
    private final VinculoRepository vinculoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final AlergiaRepository alergiaRepository;
    private final ContatoMedicoRepository contatoMedicoRepository;
    private final ConsultaRepository consultaRepository;
    private final ObservacaoPessoalRepository observacaoPessoalRepository;

    public AcessoFamiliarService(UsuarioAutenticadoService usuarioAutenticadoService,
                                 UsuarioRepository usuarioRepository,
                                 VinculoRepository vinculoRepository,
                                 MedicamentoRepository medicamentoRepository,
                                 AlergiaRepository alergiaRepository,
                                 ContatoMedicoRepository contatoMedicoRepository,
                                 ConsultaRepository consultaRepository,
                                 ObservacaoPessoalRepository observacaoPessoalRepository) {
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.usuarioRepository = usuarioRepository;
        this.vinculoRepository = vinculoRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.alergiaRepository = alergiaRepository;
        this.contatoMedicoRepository = contatoMedicoRepository;
        this.consultaRepository = consultaRepository;
        this.observacaoPessoalRepository = observacaoPessoalRepository;
    }

    public List<MedicamentoResponse> listarMedicamentos(Long idosoId) {
        Usuario idoso = validarAcessoERetornarIdoso(idosoId);
        return medicamentoRepository.findAllByUsuario(idoso).stream()
                .map(MedicamentoResponse::fromEntity)
                .toList();
    }

    public List<AlergiaResponse> listarAlergias(Long idosoId) {
        Usuario idoso = validarAcessoERetornarIdoso(idosoId);
        return alergiaRepository.findAllByUsuario(idoso).stream()
                .map(AlergiaResponse::fromEntity)
                .toList();
    }

    public List<ContatoMedicoResponse> listarContatosMedicos(Long idosoId) {
        Usuario idoso = validarAcessoERetornarIdoso(idosoId);
        return contatoMedicoRepository.findAllByUsuario(idoso).stream()
                .map(ContatoMedicoResponse::fromEntity)
                .toList();
    }

    public List<ConsultaResponse> listarConsultas(Long idosoId) {
        Usuario idoso = validarAcessoERetornarIdoso(idosoId);
        return consultaRepository.findAllByUsuario(idoso).stream()
                .map(ConsultaResponse::fromEntity)
                .toList();
    }

    public List<ObservacaoPessoalResponse> listarObservacoes(Long idosoId) {
        Usuario idoso = validarAcessoERetornarIdoso(idosoId);
        return observacaoPessoalRepository.findAllByUsuario(idoso).stream()
                .map(ObservacaoPessoalResponse::fromEntity)
                .toList();
    }

    private Usuario validarAcessoERetornarIdoso(Long idosoId) {
        Usuario familiar = usuarioAutenticadoService.getUsuarioAutenticado();

        if (familiar.getPerfil() != PerfilUsuario.FAMILIAR_AUTORIZADO) {
            throw new AcessoNegadoException("Apenas familiares autorizados podem acessar esta funcionalidade");
        }

        Usuario idoso = usuarioRepository.findById(idosoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa idosa não encontrada com id: " + idosoId));

        if (idoso.getPerfil() != PerfilUsuario.PESSOA_IDOSA) {
            throw new AcessoNegadoException("O usuário informado não é uma pessoa idosa");
        }

        boolean temVinculoAtivo = vinculoRepository.existsByIdosoAndFamiliarAndStatus(
                idoso, familiar, VinculoStatus.ATIVO
        );

        if (!temVinculoAtivo) {
            throw new AcessoNegadoException("Você não possui vínculo ativo com esta pessoa idosa");
        }

        return idoso;
    }
}
