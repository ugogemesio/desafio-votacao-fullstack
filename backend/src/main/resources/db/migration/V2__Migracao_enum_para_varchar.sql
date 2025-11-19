-- ============================
-- 1) Remover constraints que dependem das tabelas
-- ============================

ALTER TABLE voto DROP CONSTRAINT fk_voto_associado;
ALTER TABLE voto DROP CONSTRAINT fk_voto_sessao;
ALTER TABLE voto DROP CONSTRAINT uk_voto_sessao_associado;

ALTER TABLE sessao DROP CONSTRAINT fk_sessao_pauta;
ALTER TABLE sessao DROP CONSTRAINT uk_sessao_pauta;

-- ============================
-- 2) Alterar colunas ENUM → VARCHAR
-- ============================

-- Pauta.status
ALTER TABLE pauta
    ALTER COLUMN status DROP DEFAULT,
    ALTER COLUMN status TYPE VARCHAR(20);

-- Pauta.resultado_status
ALTER TABLE pauta
    ALTER COLUMN resultado_status TYPE VARCHAR(20);

-- Sessao.status
ALTER TABLE sessao
    ALTER COLUMN status TYPE VARCHAR(20);

-- Voto.opcao
ALTER TABLE voto
    ALTER COLUMN opcao TYPE VARCHAR(10);

-- ============================
-- 3) Adicionar defaults desejados
-- ============================

ALTER TABLE pauta
    ALTER COLUMN status SET DEFAULT 'CRIADA',
    ALTER COLUMN sim SET DEFAULT 0,
    ALTER COLUMN nao SET DEFAULT 0;

ALTER TABLE sessao
    ALTER COLUMN status SET DEFAULT 'ABERTA',
    ALTER COLUMN duracao_minutos SET DEFAULT 1;

-- ============================
-- 4) Criar constraints CHECK
-- ============================

ALTER TABLE pauta ADD CONSTRAINT chk_pauta_status
    CHECK (status IN ('CRIADA', 'DEFINIDA'));

ALTER TABLE pauta ADD CONSTRAINT chk_resultado_status
    CHECK (resultado_status IN ('APROVADO', 'REJEITADO', 'EMPATE'));

ALTER TABLE sessao ADD CONSTRAINT chk_sessao_status
    CHECK (status IN ('ABERTA', 'CONTANDO', 'ENCERRADA', 'FINALIZADA'));

ALTER TABLE voto ADD CONSTRAINT chk_voto_opcao
    CHECK (opcao IN ('NAO', 'SIM'));

-- ============================
-- 5) Recriar constraints de FK e UNIQUE
-- ============================

ALTER TABLE sessao
    ADD CONSTRAINT fk_sessao_pauta FOREIGN KEY (pauta_id) REFERENCES pauta(id);

ALTER TABLE sessao
    ADD CONSTRAINT uk_sessao_pauta UNIQUE (pauta_id);

ALTER TABLE voto
    ADD CONSTRAINT fk_voto_associado FOREIGN KEY (id_associado) REFERENCES associado(id);

ALTER TABLE voto
    ADD CONSTRAINT fk_voto_sessao FOREIGN KEY (id_sessao) REFERENCES sessao(id);

ALTER TABLE voto
    ADD CONSTRAINT uk_voto_sessao_associado UNIQUE (id_sessao, id_associado);

-- ============================
-- 6) Dropar os tipos ENUM antigos
-- (somente se quiser remover do banco)
-- ============================

DROP TYPE pauta_status;
DROP TYPE sessao_status;
DROP TYPE voto_opcao;
DROP TYPE resultado_status;
