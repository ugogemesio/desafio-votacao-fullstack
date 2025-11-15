package com.dbserver.ugo.votacao.Associado;
import com.dbserver.ugo.votacao.voto.Voto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idAssociado;

    @Column
    String nomeAssociado;

}
