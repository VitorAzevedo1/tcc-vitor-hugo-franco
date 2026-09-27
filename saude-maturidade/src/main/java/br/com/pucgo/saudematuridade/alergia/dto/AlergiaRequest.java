package br.com.pucgo.saudematuridade.alergia.dto;

import br.com.pucgo.saudematuridade.alergia.GravidadeAlergia;
import br.com.pucgo.saudematuridade.alergia.TipoAlergia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AlergiaRequest(

        @NotBlank(message = "A substância é obrigatória")
        @Size(max = 150, message = "A substância deve ter no máximo 150 caracteres")
        String substancia,

        @NotNull(message = "O tipo da alergia é obrigatório")
        TipoAlergia tipo,

        @NotNull(message = "A gravidade da alergia é obrigatória")
        GravidadeAlergia gravidade,

        @Size(max = 300)
        String reacao,

        LocalDate dataDescoberta,

        @Size(max = 500)
        String observacoes
) {
}