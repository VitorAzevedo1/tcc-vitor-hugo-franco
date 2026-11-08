package br.com.pucgo.saudematuridade.convite;

import br.com.pucgo.saudematuridade.auth.UsuarioAutenticadoService;
import br.com.pucgo.saudematuridade.convite.dto.ConviteResponse;
import br.com.pucgo.saudematuridade.exception.RegraDeNegocioException;
import br.com.pucgo.saudematuridade.usuario.PerfilUsuario;
import br.com.pucgo.saudematuridade.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConviteService {

    private static final String CARACTERES = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // sem O, I, 0, 1 (confundem)
    private static final int TAMANHO_CODIGO = 8;
    private static final int DIAS_VALIDADE = 7;

    private final ConviteRepository repository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final SecureRandom random = new SecureRandom();

    public ConviteService(ConviteRepository repository,
                          UsuarioAutenticadoService usuarioAutenticadoService) {
        this.repository = repository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    public ConviteResponse gerarCodigo() {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();

        if (autenticado.getPerfil() != PerfilUsuario.PESSOA_IDOSA) {
            throw new RegraDeNegocioException("Apenas pessoas idosas podem gerar códigos de convite");
        }

        String codigo = gerarCodigoUnico();
        LocalDateTime agora = LocalDateTime.now();

        Convite convite = new Convite();
        convite.setCodigo(codigo);
        convite.setIdoso(autenticado);
        convite.setStatus(ConviteStatus.PENDENTE);
        convite.setDataGeracao(agora);
        convite.setDataExpiracao(agora.plusDays(DIAS_VALIDADE));

        Convite salvo = repository.save(convite);
        return ConviteResponse.fromEntity(salvo);
    }

    public List<ConviteResponse> listarMeusConvites() {
        Usuario autenticado = usuarioAutenticadoService.getUsuarioAutenticado();

        if (autenticado.getPerfil() != PerfilUsuario.PESSOA_IDOSA) {
            throw new RegraDeNegocioException("Apenas pessoas idosas podem listar seus convites");
        }

        return repository.findAllByIdosoOrderByDataGeracaoDesc(autenticado).stream()
                .map(ConviteResponse::fromEntity)
                .toList();
    }

    private String gerarCodigoUnico() {
        String codigo;
        int tentativas = 0;
        do {
            if (tentativas++ > 10) {
                throw new RegraDeNegocioException("Falha ao gerar código único, tente novamente");
            }
            StringBuilder sb = new StringBuilder(TAMANHO_CODIGO);
            for (int i = 0; i < TAMANHO_CODIGO; i++) {
                sb.append(CARACTERES.charAt(random.nextInt(CARACTERES.length())));
            }
            codigo = sb.toString();
        } while (repository.existsByCodigo(codigo));

        return codigo;
    }
}
