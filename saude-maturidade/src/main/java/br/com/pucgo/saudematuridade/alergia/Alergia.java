package br.com.pucgo.saudematuridade.alergia;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "alergia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 150)
    private String substancia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoAlergia tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GravidadeAlergia gravidade;

    @Column(length = 300)
    private String reacao;

    @Column(name = "data_descoberta")
    private LocalDate dataDescoberta;

    @Column(length = 500)
    private String observacoes;
}
