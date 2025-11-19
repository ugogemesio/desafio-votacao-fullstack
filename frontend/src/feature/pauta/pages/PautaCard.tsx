import React from 'react';
import { Card } from '../../atoms/Card';
import { Button } from '../../atoms/Button';
import './PautaCard.scss';
import type { PautaResponseDTO } from '../types/pauta';

interface PautaCardProps {
  pauta: PautaResponseDTO;
  onResultados: (pautaId: number) => void;
  onAbrirSessao: (pautaId: number) => void;
}


export const PautaCard: React.FC<PautaCardProps> = ({
  pauta,
  onResultados,
  onAbrirSessao
}) => {
  const getStatusInfo = (status: string) => {
    switch (status) {
      case 'ABERTA':
        return { label: 'Aberta', className: 'pauta-card__status--aberta' };
      case 'VOTANDO':
        return { label: 'Em Votação', className: 'pauta-card__status--votacao' };
      case 'DEFINIDA':
        return { label: 'Fechada', className: 'pauta-card__status--fechada' };
      default:
        return { label: status, className: '' };
    }
  };

  const statusInfo = getStatusInfo(pauta.status);

  return (
    <Card className={`pauta-card ${pauta.status === 'VOTANDO' ? 'pauta-card--destaque' : ''}`} padding="md">
      <div className="pauta-card__header">
        <h3 className="pauta-card__titulo">ID Pauta - {pauta.id}</h3>
        <h3 className="pauta-card__titulo">{pauta.titulo}</h3>
        <span className={`pauta-card__status ${statusInfo.className}`}>
          {statusInfo.label}
        </span>
      </div>

      <p className="pauta-card__descricao">{pauta.descricao}</p>

      <div className="pauta-card__footer">
        <div className="pauta-card__info">
          <span className="pauta-card__data">
            Criada em: {new Date(pauta.dataCriacao).toLocaleTimeString()}
          </span>
          <span className="pauta-card__data">
            Situação: {pauta.resultado.status}
          </span>

        </div>

        <div className="pauta-card__actions">
          {pauta.status === 'ABERTA' && (
            <Button
              variant="primary"
              size="sm"
              onClick={() => onAbrirSessao(pauta.id)}
            >
              Abrir Sessão
            </Button>
          )}

          {pauta.status === 'VOTANDO' && (
            <div className="vote-actions">
              <Button
                variant="success"
                size="sm"

              >
                Votar
              </Button>
              <Button
                variant="secondary"
                size="sm"
                onClick={() => onResultados(pauta.id)}
              >
                Resultados
              </Button>
            </div>
          )}

          {pauta.status === 'DEFINIDA' && (
            <Button
              variant="secondary"
              size="sm"
              onClick={() => onResultados(pauta.id)}
            >
              Ver Resultados
            </Button>
          )}
        </div>
      </div>
    </Card>
  );
};
