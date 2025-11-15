package com.dbserver.ugo.votacao.assembleia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface AssembleiaRepository extends JpaRepository<Assembleia, Long> {
    List<Assembleia> findByNomeAssembleia(String nomeAssembleia);
    List<Assembleia> findByDataRealizacaoAssembleiaAfter(LocalDateTime data);
    List<Assembleia> findByDataRealizacaoAssembleiaBefore(LocalDateTime data);
    List<Assembleia> findByDataRealizacaoAssembleiaBetween(LocalDateTime inicio, LocalDateTime fim);
}
