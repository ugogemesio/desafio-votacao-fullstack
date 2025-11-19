import React, { useState } from 'react';
import { Modal } from '../../molecules/Modal';
import { Button } from '../../atoms/Button';
import './VotoModal.scss';

interface VotoModalProps {
  isOpen: boolean;
  onClose: () => void;
  onVotar: (voto: boolean, cpfAssociado: string) => void;
  sessaoId: number;
  isLoading?: boolean;
  erro?: string; 
}

export const VotoModal: React.FC<VotoModalProps> = ({
  isOpen,
  onClose,
  onVotar,
  sessaoId,
  isLoading = false,
  erro 
}) => {
  const [votoSelecionado, setVotoSelecionado] = useState<boolean | null>(null);
  
  const cpfSalvo = localStorage.getItem('cpfAssociado');
  console.log('CPF salvo:', cpfSalvo);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (votoSelecionado !== null && cpfSalvo) {
      onVotar(votoSelecionado, cpfSalvo);
    }
  };

  const handleClose = () => {
    setVotoSelecionado(null);
    onClose();
  };

  return (
    <Modal
      isOpen={isOpen}
      onClose={handleClose}
      title="Registrar Voto"
      size="sm"
    >
      <form onSubmit={handleSubmit} className="voto-modal">
        <div className="voto-modal__info">
          <p>Você está votando na sessão: <strong>#{sessaoId}</strong></p>
        </div>

        <div className="voto-modal__form-group">
          <label htmlFor="idAssociado" className="voto-modal__label">
            CPF do Associado:
          </label>
          <div className="voto-modal__cpf-display">
            {cpfSalvo ? (
              <strong>{cpfSalvo}</strong>
            ) : (
              <span className="voto-modal__cpf-ausente">
                CPF não encontrado. Faça login primeiro.
              </span>
            )}
          </div>
        </div>

        {erro && (
          <div className="voto-modal__erro">
            <div className="voto-modal__erro-icon">⚠️</div>
            <div className="voto-modal__erro-text">{erro}</div>
          </div>
        )}

        <div className="voto-modal__opcoes">
          <p className="voto-modal__pergunta">Qual é o seu voto?</p>
          <div className="voto-modal__botoes">
            <button
              type="button"
              className={`voto-modal__opcao ${votoSelecionado === true ? 'voto-modal__opcao--selecionada' : ''}`}
              onClick={() => setVotoSelecionado(true)}
              disabled={isLoading}
            >
              <span className="voto-modal__opcao-texto">👍 Sim</span>
            </button>
            <button
              type="button"
              className={`voto-modal__opcao ${votoSelecionado === false ? 'voto-modal__opcao--selecionada' : ''}`}
              onClick={() => setVotoSelecionado(false)}
              disabled={isLoading}
            >
              <span className="voto-modal__opcao-texto">👎 Não</span>
            </button>
          </div>
        </div>

        <div className="voto-modal__actions">
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
            disabled={isLoading || votoSelecionado === null || !cpfSalvo}
          >
            {isLoading ? 'Enviando...' : 'Confirmar Voto'}
          </Button>
        </div>
      </form>
    </Modal>
  );
};