package com.dbserver.ugo.votacao.voto;

import com.dbserver.ugo.votacao.associado.Associado;
import com.dbserver.ugo.votacao.sessao.Sessao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "voto",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_sessao", "id_associado"})
)
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_associado")
    private Associado associado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sessao")
    private Sessao sessao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VotoOpcao opcao;
}