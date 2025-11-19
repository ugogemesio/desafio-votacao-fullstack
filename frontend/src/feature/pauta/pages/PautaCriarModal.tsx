
import React, { useState } from 'react';
import { Modal } from '../../molecules/Modal';
import { Button } from '../../atoms/Button';
import './PautaCriarModal.scss';

interface PautaModalProps {
  isOpen: boolean;
  onClose: () => void;
  onCriarPauta: (data: { titulo: string; descricao: string }) => void;
  isLoading?: boolean;
}

export const PautaModal: React.FC<PautaModalProps> = ({
  isOpen,
  onClose,
  onCriarPauta,
  isLoading = false
}) => {
  const [titulo, setTitulo] = useState('');
  const [descricao, setDescricao] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onCriarPauta({ titulo, descricao });
  };

  const handleClose = () => {
    setTitulo('');
    setDescricao('');
    onClose();
  };

  return (
    <Modal
      isOpen={isOpen}
      onClose={handleClose}
      title="Criar Nova Pauta"
      size="md"
    >
      <form onSubmit={handleSubmit} className="pauta-modal">
        <div className="pauta-modal__form-group">
          <label htmlFor="titulo" className="pauta-modal__label">
            Título da Pauta:
          </label>
          <input
            type="text"
            id="titulo"
            value={titulo}
            onChange={(e) => setTitulo(e.target.value)}
            className="pauta-modal__input"
            required
            minLength={3}
            maxLength={200}
            disabled={isLoading}
            placeholder="Digite o título da pauta"
          />
        </div>

        <div className="pauta-modal__form-group">
          <label htmlFor="descricao" className="pauta-modal__label">
            Descrição:
          </label>
          <textarea
            id="descricao"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
            className="pauta-modal__textarea"
            rows={4}
            required
            minLength={10}
            maxLength={1000}
            disabled={isLoading}
            placeholder="Descreva os detalhes da pauta"
          />
        </div>

        <div className="pauta-modal__actions">
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
            disabled={isLoading || !titulo || !descricao}
          >
            {isLoading ? 'Criando...' : 'Criar Pauta'}
          </Button>
        </div>
      </form>
    </Modal>
  );
};