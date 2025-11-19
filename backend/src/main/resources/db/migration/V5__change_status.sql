-- V5__change_status.sql

-- 0. Verifica se possui algo
SELECT DISTINCT status FROM pauta;

-- 1. Remove old constraint
ALTER TABLE pauta DROP CONSTRAINT IF EXISTS chk_pauta_status;

-- 2. Normalize legacy statuses
UPDATE pauta SET status = 'ABERTA'
WHERE status = 'CRIADA';

-- 3. Apply new valid statuses
ALTER TABLE pauta
ADD CONSTRAINT chk_pauta_status
CHECK (status IN ('ABERTA', 'VOTANDO', 'DEFINIDA'));
