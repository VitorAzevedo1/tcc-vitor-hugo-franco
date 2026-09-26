package br.com.pucgo.saudematuridade.medicamento.dto;

import br.com.pucgo.saudematuridade.medicamento.Medicamento;

import java.time.LocalDate;

public record MedicamentoResponse(
        Long id,
        String nome,
        String dosagem,
        String frequencia,
        String viaAdministracao,
        LocalDate dataInicio,
        LocalDate dataFim,
        String observacoes
) {

    public static MedicamentoResponse fromEntity(Medicamento m) {
        return new MedicamentoResponse(
                m.getId(),
                m.getNome(),
                m.getDosagem(),
                m.getFrequencia(),
                m.getViaAdministracao(),
                m.getDataInicio(),
                m.getDataFim(),
                m.getObservacoes()
        );
    }
}
