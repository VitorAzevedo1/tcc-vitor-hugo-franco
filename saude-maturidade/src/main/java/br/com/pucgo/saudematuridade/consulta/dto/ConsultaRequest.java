package br.com.pucgo.saudematuridade.consulta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ConsultaRequest(

        @NotNull(message = "A data e hora da consulta são obrigatórias")
        LocalDateTime dataHora,

        @NotBlank(message = "O nome do médico é obrigatório")
        @Size(max = 150)
        String medico,

        @Size(max = 100)
        String especialidade,

        @Size(max = 200)
        String local,

        @Size(max = 300)
        String motivo,

        @Size(max = 500)
        String diagnostico,

        @Size(max = 500)
        String prescricao,

        @Size(max = 500)
        String observacoes
) {
}