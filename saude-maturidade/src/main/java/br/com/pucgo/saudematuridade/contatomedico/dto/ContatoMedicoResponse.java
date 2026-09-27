package br.com.pucgo.saudematuridade.contatomedico.dto;

import br.com.pucgo.saudematuridade.contatomedico.ContatoMedico;

public record ContatoMedicoResponse(
        Long id,
        String nome,
        String especialidade,
        String telefone,
        String email,
        String crm,
        String localAtendimento,
        String endereco,
        String observacoes
) {

    public static ContatoMedicoResponse fromEntity(ContatoMedico c) {
        return new ContatoMedicoResponse(
                c.getId(),
                c.getNome(),
                c.getEspecialidade(),
                c.getTelefone(),
                c.getEmail(),
                c.getCrm(),
                c.getLocalAtendimento(),
                c.getEndereco(),
                c.getObservacoes()
        );
    }
}
