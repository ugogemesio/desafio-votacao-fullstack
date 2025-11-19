package com.dbserver.ugo.votacao.sessao;

import com.dbserver.ugo.votacao.resultado.Resultado;
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

    @ManyToOne
    @JoinColumn(name = "pauta_id", unique = true, nullable = false)
    private Pauta pauta;

    @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voto> votos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessaoStatus status;

    @PrePersist
    void onCreate() {
        this.abertura = LocalDateTime.now();
        this.duracaoMinutos = (duracaoMinutos == null ? 1 : duracaoMinutos);
        this.fechamento = abertura.plusMinutes(duracaoMinutos == null ? 1 : duracaoMinutos);
        this.status = SessaoStatus.ABERTA;
    }
}
