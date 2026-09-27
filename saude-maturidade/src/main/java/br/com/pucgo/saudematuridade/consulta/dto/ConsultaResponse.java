package br.com.pucgo.saudematuridade.consulta.dto;

import br.com.pucgo.saudematuridade.consulta.Consulta;

import java.time.LocalDateTime;

public record ConsultaResponse(
        Long id,
        LocalDateTime dataHora,
        String medico,
        String especialidade,
        String local,
        String motivo,
        String diagnostico,
        String prescricao,
        String observacoes
) {

    public static ConsultaResponse fromEntity(Consulta c) {
        return new ConsultaResponse(
                c.getId(),
                c.getDataHora(),
                c.getMedico(),
                c.getEspecialidade(),
                c.getLocal(),
                c.getMotivo(),
                c.getDiagnostico(),
                c.getPrescricao(),
                c.getObservacoes()
        );
    }
}
