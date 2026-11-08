package br.com.pucgo.saudematuridade.vinculo;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "vinculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vinculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idoso_id", nullable = false)
    private Usuario idoso;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "familiar_id", nullable = false)
    private Usuario familiar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private VinculoStatus status;

    @Column(name = "data_vinculo", nullable = false)
    private LocalDateTime dataVinculo;

    @Column(name = "data_revogacao")
    private LocalDateTime dataRevogacao;
}
