import React from 'react';
import { Modal } from '../../molecules/Modal';
import { Button } from '../../atoms/Button';
// import './ResultadoModal.scss';
import type { Resultado } from '../../pauta/types/pauta';

interface ResultadoModalProps {
  isOpen: boolean;
  onClose: () => void;
  resultado: Resultado | null;
  pautaTitulo?: string;
}

export const ResultadoModal: React.FC<ResultadoModalProps> = ({
  isOpen,
  onClose,
  resultado,
  pautaTitulo = 'pauta selecionada'
}) => {
  if (!resultado) return null;

  return (
    <Modal
      isOpen={isOpen}
      onClose={onClose}
      title={`Resultado da Pauta: ${pautaTitulo}`}
      size="sm"
    >
      <div className="resultado-modal">
        <p><strong>Sim:</strong> {resultado.sim}</p>
        <p><strong>Não:</strong> {resultado.nao}</p>
        <p><strong>Status:</strong> {resultado.status}</p>

        <div className="resultado-modal__actions">
          <Button variant="secondary" onClick={onClose}>
            Fechar
          </Button>
        </div>
      </div>
    </Modal>
  );
};
