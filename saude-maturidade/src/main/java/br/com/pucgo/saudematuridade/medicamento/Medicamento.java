package br.com.pucgo.saudematuridade.medicamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "medicamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 50)
    private String dosagem;

    @Column(nullable = false, length = 100)
    private String frequencia;

    @Column(name = "via_administracao", length = 50)
    private String viaAdministracao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(length = 500)
    private String observacoes;
}
