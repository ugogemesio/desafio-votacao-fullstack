package com.dbserver.ugo.votacao.sessao;

import com.dbserver.ugo.votacao.pauta.Pauta;
import com.dbserver.ugo.votacao.voto.Voto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSessao;

    @Column
    private String assunto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pauta")
    private Pauta pauta;

    @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voto> votos;

}
