import React from 'react';
import { Card } from '../../atoms/Card';
import { Button } from '../../atoms/Button';
import { Gauge } from '../../molecules/gauge/Gauge';
import './PautaCard.scss';
import type { PautaResponseDTO } from '../types/pauta';

interface PautaCardProps {
  pauta: PautaResponseDTO;
  onResultados: (pautaId: number) => void;
  onAbrirSessao: (pautaId: number) => void;
}

export const PautaCard: React.FC<PautaCardProps> = ({
  pauta,
  onAbrirSessao
}) => {

  const statusMap = {
    ABERTA: { label: 'Aberta', className: 'pauta-card__status--aberta' },
    VOTANDO: { label: 'Em Votação', className: 'pauta-card__status--votacao' },
    DEFINIDA: { label: 'Fechada', className: 'pauta-card__status--fechada' }
  };

  const statusInfo = statusMap[pauta.status] || {
    label: pauta.status,
    className: ''
  };

  return (
    <Card
      className={`pauta-card ${pauta.status === 'VOTANDO' ? 'pauta-card--destaque' : ''}`}
      padding="md"
    >
      <div className="pauta-card__header">
        <h3>ID Pauta - {pauta.id}</h3>
        <h3>{pauta.titulo}</h3>
        <span className={`pauta-card__status ${statusInfo.className}`}>
          {statusInfo.label}
        </span>
      </div>

      <p className="pauta-card__descricao">{pauta.descricao}</p>

      <div className="pauta-card__footer">
        <div className="pauta-card__info">
          <span className="pauta-card__data">
            Criada em: {new Date(pauta.dataCriacao).toLocaleDateString('pt-BR')}
          </span>

          {pauta.status === 'DEFINIDA' && pauta.resultado && (
            <div className="pauta-card__resultado">
              <Gauge
                sim={pauta.resultado.sim}
                nao={pauta.resultado.nao}
              />
            </div>
          )}
        </div>

        {pauta.status === 'ABERTA' && (
          <Button
            variant="primary"
            size="sm"
            onClick={() => onAbrirSessao(pauta.id)}
          >
            Abrir Sessão
          </Button>
        )}
      </div>
    </Card>
  );
};
