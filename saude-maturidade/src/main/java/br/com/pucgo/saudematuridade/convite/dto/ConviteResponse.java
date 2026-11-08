package br.com.pucgo.saudematuridade.convite.dto;

import br.com.pucgo.saudematuridade.convite.Convite;
import br.com.pucgo.saudematuridade.convite.ConviteStatus;

import java.time.LocalDateTime;

public record ConviteResponse(
        Long id,
        String codigo,
        ConviteStatus status,
        LocalDateTime dataGeracao,
        LocalDateTime dataExpiracao,
        LocalDateTime dataUtilizacao
) {

    public static ConviteResponse fromEntity(Convite c) {
        return new ConviteResponse(
                c.getId(),
                c.getCodigo(),
                c.getStatus(),
                c.getDataGeracao(),
                c.getDataExpiracao(),
                c.getDataUtilizacao()
        );
    }
}
