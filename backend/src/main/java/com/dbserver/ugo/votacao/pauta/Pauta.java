package com.dbserver.ugo.votacao.pauta;

import com.dbserver.ugo.votacao.resultado.Resultado;
import com.dbserver.ugo.votacao.sessao.Sessao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pauta")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "pauta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sessao> sessoes = new ArrayList<>();

    @Embedded
    private Resultado resultado; //afim de simplificar, há resultado da pauta e resultado da sessao

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PautaStatus status;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = PautaStatus.ABERTA;
        }
        if (this.dataCriacao == null) {
            this.dataCriacao = LocalDateTime.now();
        }
        if (this.resultado == null) {
            this.resultado = new Resultado(0, 0);
        }
    }
}