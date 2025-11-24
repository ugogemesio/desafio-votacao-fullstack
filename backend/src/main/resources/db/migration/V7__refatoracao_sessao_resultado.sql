-- ======================================================
-- MIGRAÇÃO: Resultado passa para SESSAO e ajustes de status
-- ======================================================

-- 1. Adicionar colunas na tabela sessao (seguro se já existirem)
ALTER TABLE sessao ADD COLUMN IF NOT EXISTS sim BIGINT DEFAULT 0;
ALTER TABLE sessao ADD COLUMN IF NOT EXISTS nao BIGINT DEFAULT 0;
ALTER TABLE sessao ADD COLUMN IF NOT EXISTS resultado_status VARCHAR(20);

-- ======================================================
-- 2. Constraint de resultado da sessão
-- ======================================================
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'chk_sessao_resultado_status'
    ) THEN
        ALTER TABLE sessao
        ADD CONSTRAINT chk_sessao_resultado_status
        CHECK (resultado_status IN ('APROVADO','REJEITADO','EMPATE'));
    END IF;
END $$;

-- ======================================================
-- 3. Garantir defaults
-- ======================================================
ALTER TABLE sessao
    ALTER COLUMN sim SET DEFAULT 0,
    ALTER COLUMN nao SET DEFAULT 0;

-- ======================================================
-- 4. Atualizar CHECK do status da pauta
-- ======================================================

ALTER TABLE pauta DROP CONSTRAINT IF EXISTS chk_pauta_status;

ALTER TABLE pauta
ADD CONSTRAINT chk_pauta_status
CHECK (status IN ('ABERTA','VOTANDO','APROVADA','REPROVADA'));

-- Converter registros antigos DEFINIDA
UPDATE pauta
SET status = 'REPROVADA'
WHERE status = 'DEFINIDA'
  AND resultado_status = 'REJEITADO';

UPDATE pauta
SET status = 'APROVADA'
WHERE status = 'DEFINIDA'
  AND resultado_status = 'APROVADO';

-- ======================================================
-- 5. Recalcular votos por sessão
-- ======================================================

UPDATE sessao s
SET
  sim = COALESCE(v.sim_count, 0),
  nao = COALESCE(v.nao_count, 0)
FROM (
  SELECT
    v2.id_sessao AS sessao_id,
    COUNT(*) FILTER (WHERE v2.opcao = 'SIM') AS sim_count,
    COUNT(*) FILTER (WHERE v2.opcao = 'NAO') AS nao_count
  FROM voto v2
  GROUP BY v2.id_sessao
) v
WHERE s.id = v.sessao_id;

-- ======================================================
-- 6. Definir resultado da sessão automaticamente
-- ======================================================

UPDATE sessao
SET resultado_status =
    CASE
        WHEN sim > nao THEN 'APROVADO'
        WHEN sim < nao THEN 'REJEITADO'
        ELSE 'EMPATE'
    END
WHERE resultado_status IS NULL;
