package com.dbserver.ugo.votacao.assembleia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface AssembleiaRepository extends JpaRepository<Assembleia, Long> {
    List<Assembleia> findByNomeAssembleia(String nomeAssembleia);
}
