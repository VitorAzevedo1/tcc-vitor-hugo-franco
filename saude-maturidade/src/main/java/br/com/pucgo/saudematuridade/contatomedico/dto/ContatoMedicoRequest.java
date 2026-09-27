package br.com.pucgo.saudematuridade.contatomedico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContatoMedicoRequest(

        @NotBlank(message = "O nome do médico é obrigatório")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
        String nome,

        @NotBlank(message = "A especialidade é obrigatória")
        @Size(max = 100)
        String especialidade,

        @NotBlank(message = "O telefone é obrigatório")
        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
        String telefone,

        @Email(message = "Email inválido")
        @Size(max = 150)
        String email,

        @Size(max = 20)
        String crm,

        @Size(max = 200)
        String localAtendimento,

        @Size(max = 300)
        String endereco,

        @Size(max = 500)
        String observacoes
) {
}
