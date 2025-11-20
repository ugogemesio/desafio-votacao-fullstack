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

interface GaugeProps {
  sim: number;
  nao: number;
  size?: number;
}

const Gauge: React.FC<GaugeProps> = ({ sim, nao, size = 80 }) => {
  const total = sim + nao;
  const percentage = total > 0 ? (sim / total) * 100 : 0;
  const strokeWidth = 8;
  const radius = (size - strokeWidth) / 2;
  const circumference = 2 * Math.PI * radius;
  const strokeDasharray = circumference;
  const strokeDashoffset = circumference - (percentage / 100) * circumference;

  const getGaugeColor = () => {
    if (total === 0) return '#6c757d'; // Cinza para sem votos
    if (percentage > 60) return '#28a745'; // Verde para aprovado
    if (percentage < 40) return '#dc3545'; // Vermelho para rejeitado
    return '#ffc107'; // Amarelo para empate/indefinido
  };

  const getGaugeLabel = () => {
    if (total === 0) return 'Sem aclamação';
    if (percentage > 60) return 'Aprovada';
    if (percentage < 40) return 'Rejeitada';
    return 'Empate';
  };

  return (
    <div className="gauge-container" style={{ width: size, height: size }}>
      <svg width={size} height={size} className="gauge">
        {/* Fundo do gauge */}
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          stroke="#e9ecef"
          strokeWidth={strokeWidth}
          fill="none"
        />
        {/* Preenchimento do gauge */}
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          stroke={getGaugeColor()}
          strokeWidth={strokeWidth}
          fill="none"
          strokeDasharray={strokeDasharray}
          strokeDashoffset={strokeDashoffset}
          strokeLinecap="round"
          transform={`rotate(-90 ${size / 2} ${size / 2})`}
          className="gauge__fill"
        />
        {/* Texto central */}
        <text
          x="50%"
          y="45%"
          textAnchor="middle"
          className="gauge__percentage"
          fontSize={total > 0 ? "14" : "10"}
          fontWeight="bold"
          fill={getGaugeColor()}
        >
          {total > 0 ? `${Math.round(percentage)}%` : '—'}
        </text>
        <text
          x="50%"
          y="65%"
          textAnchor="middle"
          className="gauge__label"
          fontSize="8"
          fill="#6c757d"
        >
          {getGaugeLabel()}
        </text>
      </svg>
      
      {/* Legenda dos votos */}
      {total > 0 && (
        <div className="gauge__legend">
          <div className="gauge__legend-item">
            <span className="gauge__legend-color" style={{ backgroundColor: '#28a745' }}></span>
            <span className="gauge__legend-text">Sim: {sim}</span>
          </div>
          <div className="gauge__legend-item">
            <span className="gauge__legend-color" style={{ backgroundColor: '#dc3545' }}></span>
            <span className="gauge__legend-text">Não: {nao}</span>
          </div>
        </div>
      )}
    </div>
  );
};

export const PautaCard: React.FC<PautaCardProps> = ({
  pauta,
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
            Criada em: {new Date(pauta.dataCriacao).toLocaleDateString('pt-BR')}
          </span>
          
          {pauta.status === 'DEFINIDA' && (
            <div className="pauta-card__resultado">
              <Gauge sim={pauta.resultado.sim} nao={pauta.resultado.nao} />
            </div>
          )}
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
        </div>
      </div>
    </Card>
  );
};