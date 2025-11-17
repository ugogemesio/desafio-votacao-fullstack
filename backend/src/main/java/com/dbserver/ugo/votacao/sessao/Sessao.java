package com.dbserver.ugo.votacao.sessao;

import com.dbserver.ugo.votacao.pauta.Pauta;
import com.dbserver.ugo.votacao.voto.Voto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessao")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime abertura;

    @Column(nullable = false)
    private LocalDateTime fechamento;

    @Column(nullable = false)
    private Integer duracaoMinutos;

    @OneToOne
    @JoinColumn(name = "pauta_id", unique = true, nullable = false)
    private Pauta pauta;

    @OneToMany(mappedBy = "sessao")
    private List<Voto> votos;
}
