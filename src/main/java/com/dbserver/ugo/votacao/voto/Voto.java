package com.dbserver.ugo.votacao.voto;

import com.dbserver.ugo.votacao.associado.Associado;
import com.dbserver.ugo.votacao.sessao.Sessao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "voto")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_associado")
    private Associado associado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sessao")
    private Sessao sessao;

    @Column(nullable = false)
    private boolean valor;

}
