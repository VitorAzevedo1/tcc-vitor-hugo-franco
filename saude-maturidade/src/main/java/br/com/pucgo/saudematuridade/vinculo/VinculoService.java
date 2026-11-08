package br.com.pucgo.saudematuridade.vinculo;

import br.com.pucgo.saudematuridade.auth.UsuarioAutenticadoService;
import br.com.pucgo.saudematuridade.convite.Convite;
import br.com.pucgo.saudematuridade.convite.ConviteRepository;
import br.com.pucgo.saudematuridade.convite.ConviteStatus;
import br.com.pucgo.saudematuridade.exception.RecursoNaoEncontradoException;
import br.com.pucgo.saudematuridade.exception.RegraDeNegocioException;
import br.com.pucgo.saudematuridade.usuario.PerfilUsuario;
import br.com.pucgo.saudematuridade.usuario.Usuario;
import br.com.pucgo.saudematuridade.vinculo.dto.AceitarVinculoRequest;
import br.com.pucgo.saudematuridade.vinculo.dto.VinculoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VinculoService {

    private final VinculoRepository vinculoRepository;
    private final ConviteRepository conviteRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public VinculoService(VinculoRepository vinculoRepository,
                          ConviteRepository conviteRepository,
                          UsuarioAutenticadoService usuarioAutenticadoService) {
        this.vinculoRepository = vinculoRepository;
        this.conviteRepository = conviteRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    @Transactional
    public VinculoResponse aceitarVinculo(AceitarVinculoRequest request) {
        Usuario familiar = usuarioAutenticadoService.getUsuarioAutenticado();

        if (familiar.getPerfil() != PerfilUsuario.FAMILIAR_AUTORIZADO) {
            throw new RegraDeNegocioException("Apenas familiares autorizados podem aceitar códigos de convite");
        }

        Convite convite = conviteRepository.findByCodigo(request.codigo())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Código de convite inválido"));

        if (convite.getStatus() != ConviteStatus.PENDENTE) {
            throw new RegraDeNegocioException("Este convite não está mais disponível");
        }

        if (LocalDateTime.now().isAfter(convite.getDataExpiracao())) {
            convite.setStatus(ConviteStatus.EXPIRADO);
            conviteRepository.save(convite);
            throw new RegraDeNegocioException("Este convite expirou");
        }

        if (convite.getIdoso().getId().equals(familiar.getId())) {
            throw new RegraDeNegocioException("Você não pode aceitar o próprio convite");
        }

        if (vinculoRepository.existsByIdosoAndFamiliarAndStatus(convite.getIdoso(), familiar, VinculoStatus.ATIVO)) {
            throw new RegraDeNegocioException("Já existe um vínculo ativo com esta pessoa idosa");
        }

        LocalDateTime agora = LocalDateTime.now();

        Vinculo vinculo = new Vinculo();
        vinculo.setIdoso(convite.getIdoso());
        vinculo.setFamiliar(familiar);
        vinculo.setStatus(VinculoStatus.ATIVO);
        vinculo.setDataVinculo(agora);
        Vinculo salvo = vinculoRepository.save(vinculo);

        convite.setStatus(ConviteStatus.UTILIZADO);
        convite.setDataUtilizacao(agora);
        conviteRepository.save(convite);

        return VinculoResponse.fromEntity(salvo);
    }

    public List<VinculoResponse> listarMeusFamiliares() {
        Usuario idoso = usuarioAutenticadoService.getUsuarioAutenticado();

        if (idoso.getPerfil() != PerfilUsuario.PESSOA_IDOSA) {
            throw new RegraDeNegocioException("Apenas pessoas idosas podem listar seus familiares vinculados");
        }

        return vinculoRepository.findAllByIdosoAndStatus(idoso, VinculoStatus.ATIVO).stream()
                .map(VinculoResponse::fromEntity)
                .toList();
    }

    public List<VinculoResponse> listarMeusIdosos() {
        Usuario familiar = usuarioAutenticadoService.getUsuarioAutenticado();

        if (familiar.getPerfil() != PerfilUsuario.FAMILIAR_AUTORIZADO) {
            throw new RegraDeNegocioException("Apenas familiares autorizados podem listar idosos vinculados");
        }

        return vinculoRepository.findAllByFamiliarAndStatus(familiar, VinculoStatus.ATIVO).stream()
                .map(VinculoResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void revogar(Long vinculoId) {
        Usuario idoso = usuarioAutenticadoService.getUsuarioAutenticado();

        if (idoso.getPerfil() != PerfilUsuario.PESSOA_IDOSA) {
            throw new RegraDeNegocioException("Apenas pessoas idosas podem revogar vínculos");
        }

        Vinculo vinculo = vinculoRepository.findByIdAndIdoso(vinculoId, idoso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vínculo não encontrado"));

        if (vinculo.getStatus() == VinculoStatus.REVOGADO) {
            throw new RegraDeNegocioException("Este vínculo já foi revogado");
        }

        vinculo.setStatus(VinculoStatus.REVOGADO);
        vinculo.setDataRevogacao(LocalDateTime.now());
        vinculoRepository.save(vinculo);
    }
}
