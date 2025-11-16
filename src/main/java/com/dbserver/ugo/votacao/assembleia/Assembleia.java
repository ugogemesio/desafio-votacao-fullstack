package com.dbserver.ugo.votacao.assembleia;

import com.dbserver.ugo.votacao.pauta.Pauta;
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
@Table(name ="assembleia")
public class Assembleia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAssembleia;

    @Column
    private String nomeAssembleia;

    @Column
    private String descricaoAssembleia;

    @Column
    private LocalDateTime dataRealizacaoAssembleia;

    @OneToMany(mappedBy = "assembleia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pauta> pautas;

}
