package com.dbserver.ugo.votacao.voto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    boolean existsByAssociadoIdAndSessaoId(Long associadoId, Long sessaoId);


}
