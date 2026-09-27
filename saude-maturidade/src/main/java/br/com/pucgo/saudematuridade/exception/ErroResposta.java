package br.com.pucgo.saudematuridade.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResposta(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        String caminho,
        List<CampoInvalido> camposInvalidos
) {

    public record CampoInvalido(String campo, String mensagem) {}

    public static ErroResposta simples(int status, String erro, String mensagem, String caminho) {
        return new ErroResposta(LocalDateTime.now(), status, erro, mensagem, caminho, null);
    }

    public static ErroResposta comCampos(int status, String erro, String mensagem, String caminho, List<CampoInvalido> campos) {
        return new ErroResposta(LocalDateTime.now(), status, erro, mensagem, caminho, campos);
    }
}