package br.com.pucgo.saudematuridade.medicamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MedicamentoRequest(

        @NotBlank(message = "O nome do medicamento é obrigatório")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
        String nome,

        @NotBlank(message = "A dosagem é obrigatória")
        @Size(max = 50)
        String dosagem,

        @NotBlank(message = "A frequência é obrigatória")
        @Size(max = 100)
        String frequencia,

        @Size(max = 50)
        String viaAdministracao,

        @NotNull(message = "A data de início é obrigatória")
        LocalDate dataInicio,

        LocalDate dataFim,

        @Size(max = 500)
        String observacoes
) {
}