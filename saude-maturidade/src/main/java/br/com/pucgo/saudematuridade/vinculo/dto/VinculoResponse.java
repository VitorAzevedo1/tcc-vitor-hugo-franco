package br.com.pucgo.saudematuridade.vinculo.dto;

import br.com.pucgo.saudematuridade.vinculo.Vinculo;
import br.com.pucgo.saudematuridade.vinculo.VinculoStatus;

import java.time.LocalDateTime;

public record VinculoResponse(
        Long id,
        Long idosoId,
        String idosoNome,
        Long familiarId,
        String familiarNome,
        VinculoStatus status,
        LocalDateTime dataVinculo,
        LocalDateTime dataRevogacao
) {

    public static VinculoResponse fromEntity(Vinculo v) {
        return new VinculoResponse(
                v.getId(),
                v.getIdoso().getId(),
                v.getIdoso().getNome(),
                v.getFamiliar().getId(),
                v.getFamiliar().getNome(),
                v.getStatus(),
                v.getDataVinculo(),
                v.getDataRevogacao()
        );
    }
}
