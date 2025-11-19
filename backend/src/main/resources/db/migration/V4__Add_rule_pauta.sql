-- Remover a constraint atual se existir
ALTER TABLE pauta DROP CONSTRAINT IF EXISTS chk_pauta_status;

-- Adicionar a nova constraint com o STATUS VOTANDO
ALTER TABLE pauta ADD CONSTRAINT chk_pauta_status
    CHECK (status IN ('CRIADA', 'DEFINIDA', 'VOTANDO'));