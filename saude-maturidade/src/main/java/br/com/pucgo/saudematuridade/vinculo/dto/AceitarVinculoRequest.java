package br.com.pucgo.saudematuridade.vinculo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AceitarVinculoRequest(

        @NotBlank(message = "O código do convite é obrigatório")
        @Size(min = 8, max = 10, message = "Código inválido")
        String codigo
) {
}