package br.com.pucgo.saudematuridade.alergia.dto;

import br.com.pucgo.saudematuridade.alergia.Alergia;
import br.com.pucgo.saudematuridade.alergia.GravidadeAlergia;
import br.com.pucgo.saudematuridade.alergia.TipoAlergia;

import java.time.LocalDate;

public record AlergiaResponse(
        Long id,
        String substancia,
        TipoAlergia tipo,
        GravidadeAlergia gravidade,
        String reacao,
        LocalDate dataDescoberta,
        String observacoes
) {

    public static AlergiaResponse fromEntity(Alergia a) {
        return new AlergiaResponse(
                a.getId(),
                a.getSubstancia(),
                a.getTipo(),
                a.getGravidade(),
                a.getReacao(),
                a.getDataDescoberta(),
                a.getObservacoes()
        );
    }
}
