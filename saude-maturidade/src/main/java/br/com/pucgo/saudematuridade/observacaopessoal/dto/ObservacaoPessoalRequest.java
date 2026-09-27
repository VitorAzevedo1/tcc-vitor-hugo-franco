package br.com.pucgo.saudematuridade.observacaopessoal.dto;

import br.com.pucgo.saudematuridade.observacaopessoal.CategoriaObservacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ObservacaoPessoalRequest(

        @NotBlank(message = "O título é obrigatório")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
        String titulo,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres")
        String descricao,

        @NotNull(message = "A data e hora da observação são obrigatórias")
        LocalDateTime dataHora,

        @NotNull(message = "A categoria é obrigatória")
        CategoriaObservacao categoria
) {
}