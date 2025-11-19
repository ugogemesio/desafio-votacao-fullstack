
import React, { useState } from 'react';
import { Modal } from '../../molecules/Modal';
import { Button } from '../../atoms/Button';
import './SessaoModal.scss';

interface SessaoModalProps {
  isOpen: boolean;
  onClose: () => void;
  onCriarSessao: (duracaoMinutos: number) => void;
  pautaId: number;
  pautaTitulo?: string;
  isLoading?: boolean;
}

export const SessaoModal: React.FC<SessaoModalProps> = ({
  isOpen,
  onClose,
  onCriarSessao,
  pautaId,
  pautaTitulo = 'pauta selecionada',
  isLoading = false
}) => {
  const [duracao, setDuracao] = useState<number>(1);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onCriarSessao(duracao);
  };

  const handleClose = () => {
    setDuracao(1); 
    onClose();
  };

  return (
    <Modal
      isOpen={isOpen}
      onClose={handleClose}
      title="Abrir Sessão de Votação"
      size="sm"
    >
      <form onSubmit={handleSubmit} className="sessao-modal">
        <div className="sessao-modal__info">
          <p>
            Você está abrindo uma sessão de votação para a pauta: 
            <strong> {pautaTitulo}</strong>
          </p>
          <p className="sessao-modal__pauta-id">ID da Pauta: {pautaId}</p>
        </div>

        <div className="sessao-modal__form-group">
          <label htmlFor="duracao" className="sessao-modal__label">
            Duração da Sessão (minutos):
          </label>
          <input
            type="number"
            id="duracao"
            min="1"
            max="1440" 
            value={duracao}
            onChange={(e) => setDuracao(Number(e.target.value))}
            className="sessao-modal__input"
            required
            disabled={isLoading}
          />
          <small className="sessao-modal__help">
            A sessão ficará aberta por este período para votação
          </small>
        </div>

        <div className="sessao-modal__actions">
          <Button
            type="button"
            variant="secondary"
            onClick={handleClose}
            disabled={isLoading}
          >
            Cancelar
          </Button>
          <Button
            type="submit"
            variant="primary"
            disabled={isLoading || duracao < 1}
          >
            {isLoading ? 'Abrindo...' : 'Abrir Sessão'}
          </Button>
        </div>
      </form>
    </Modal>
  );
};