package br.com.pucgo.saudematuridade.consulta;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "consulta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false, length = 150)
    private String medico;

    @Column(length = 100)
    private String especialidade;

    @Column(length = 200)
    private String local;

    @Column(length = 300)
    private String motivo;

    @Column(length = 500)
    private String diagnostico;

    @Column(length = 500)
    private String prescricao;

    @Column(length = 500)
    private String observacoes;
}