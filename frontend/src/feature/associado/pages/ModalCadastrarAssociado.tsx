import React, { useState } from 'react';
import { Button } from '../../atoms/Button';
import { Input } from '../../atoms/Input';
import { Card } from '../../atoms/Card';
import './ModalCadastrarAssociado.scss';

interface ModalCadastrarAssociadoProps {
  onClose: () => void;
  onSalvar: (nome: string) => void;
  loading?: boolean;
}

const ModalCadastrarAssociado: React.FC<ModalCadastrarAssociadoProps> = ({
  onClose,
  onSalvar,
  loading = false
}) => {
  const [nome, setNome] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (nome.trim()) {
      onSalvar(nome.trim());
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <Card className="modal-card" padding="lg">
          <div className="modal-header">
            <h2 className="modal-title">Cadastro de Associado</h2>
            <p className="modal-subtitle">
              Complete seu cadastro para participar das votações
            </p>
          </div>

          <form onSubmit={handleSubmit} className="modal-form">
            <Input
              type="text"
              label="Nome Completo"
              placeholder="Digite seu nome completo"
              value={nome}
              onChange={setNome}
              disabled={loading}
              autoFocus
            />

            <div className="modal-actions">
              <Button
                type="button"
                variant="secondary"
                onClick={onClose}
                disabled={loading}
              >
                Cancelar
              </Button>
              <Button
                type="submit"
                variant="primary"
                disabled={!nome.trim() || loading}
                loading={loading}
              >
                {loading ? "Salvando..." : "Confirmar Cadastro"}
              </Button>
            </div>
          </form>
        </Card>
      </div>
    </div>
  );
};

export default ModalCadastrarAssociado;