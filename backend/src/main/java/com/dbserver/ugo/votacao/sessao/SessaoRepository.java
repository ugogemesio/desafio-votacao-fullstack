package com.dbserver.ugo.votacao.sessao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Query("SELECT s FROM Sessao s WHERE s.pauta.id = :pautaId")
    Optional<Sessao> findByPautaId(Long pautaId);

    @Query("""
        SELECT s FROM Sessao s
        JOIN FETCH s.pauta
    """)
    List<Sessao> findAllWithPauta();

    List<Sessao> findByStatus(SessaoStatus status);

    @Query("""
    SELECT s FROM Sessao s 
    WHERE s.status = :status 
    AND s.fechamento < :agora
""")
    List<Sessao> findByStatusAndFechamentoBefore(
            @Param("status") SessaoStatus status,
            @Param("agora") LocalDateTime agora
    );


}