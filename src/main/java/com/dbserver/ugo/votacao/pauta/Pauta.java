package com.dbserver.ugo.votacao.pauta;

import com.dbserver.ugo.votacao.assembleia.Assembleia;
import com.dbserver.ugo.votacao.sessao.Sessao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="pauta")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPauta;

    @Column
    private String descricaoPauta;

    @Column(nullable = false)
    private String titulo;

    @OneToMany(mappedBy = "pauta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sessao> sessoes;

    @ManyToOne
    @JoinColumn(name = "id_assembleia", nullable = false)
    private Assembleia assembleia;

}
