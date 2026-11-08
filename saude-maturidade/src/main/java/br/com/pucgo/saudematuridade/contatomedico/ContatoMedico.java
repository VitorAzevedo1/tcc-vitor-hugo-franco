package br.com.pucgo.saudematuridade.contatomedico;

import br.com.pucgo.saudematuridade.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contato_medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContatoMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 100)
    private String especialidade;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String crm;

    @Column(name = "local_atendimento", length = 200)
    private String localAtendimento;

    @Column(length = 300)
    private String endereco;

    @Column(length = 500)
    private String observacoes;
}