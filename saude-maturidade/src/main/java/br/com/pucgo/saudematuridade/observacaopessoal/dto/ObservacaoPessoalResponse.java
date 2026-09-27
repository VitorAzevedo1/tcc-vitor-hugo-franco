package br.com.pucgo.saudematuridade.observacaopessoal.dto;

import br.com.pucgo.saudematuridade.observacaopessoal.CategoriaObservacao;
import br.com.pucgo.saudematuridade.observacaopessoal.ObservacaoPessoal;

import java.time.LocalDateTime;

public record ObservacaoPessoalResponse(
        Long id,
        String titulo,
        String descricao,
        LocalDateTime dataHora,
        CategoriaObservacao categoria
) {

    public static ObservacaoPessoalResponse fromEntity(ObservacaoPessoal o) {
        return new ObservacaoPessoalResponse(
                o.getId(),
                o.getTitulo(),
                o.getDescricao(),
                o.getDataHora(),
                o.getCategoria()
        );
    }
}
