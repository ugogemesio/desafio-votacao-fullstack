import React from 'react';
import { Card } from '../../atoms/Card';
import { Button } from '../../atoms/Button';
import { Gauge } from '../../molecules/gauge/Gauge';
import './SessaoCard.scss';
import type { SessaoPautaDTO } from '../types/sessao';

interface SessaoCardProps {
  sessao: SessaoPautaDTO;
  onVotar: (sessaoId: number) => void;
  onResultados: (sessaoId: number) => void;
}

export const SessaoCard: React.FC<SessaoCardProps> = ({
  sessao,
  onVotar
}) => {
  const getStatusInfo = (status: string) => {
    switch (status) {
      case 'ABERTA':
        return { label: 'Aberta', className: 'sessao-card__status--aberta' };
      case 'ENCERRADA':
        return { label: 'Encerrada', className: 'sessao-card__status--fechada' };
      default:
        return { label: status, className: '' };
    }
  };

  const statusInfo = getStatusInfo(sessao.status);
  const formatarData = (data: string) => {
    return new Date(data).toLocaleString('pt-BR');
  };

  return (
    <Card className={`sessao-card ${sessao.status === 'ABERTA' ? 'sessao-card--destaque' : ''}`} padding="md">
      <div className="sessao-card__header">
        <h3 className="sessao-card__titulo">Sessão #{sessao.id}</h3>
        <span className={`sessao-card__status ${statusInfo.className}`}>
          {statusInfo.label}
        </span>
      </div>
      
      <div className="sessao-card__content">
        <div className="sessao-card__info">
          <span className="sessao-card__data">
            <strong>Abertura:</strong> {formatarData(sessao.abertura)}
          </span>
          <span className="sessao-card__data">
            <strong>Fechamento:</strong> {formatarData(sessao.fechamento)}
          </span>
          <span className="sessao-card__duracao">
            <strong>Duração:</strong> {sessao.duracaoMinutos} minutos
          </span>
          <span className="sessao-card__pauta">
            <strong>Pauta ID:</strong> {sessao.pautaId}
          </span>
          <span className="sessao-card__pauta">
            <strong>Nome da Pauta:</strong> {sessao.pautaTitulo}
          </span>
          <span className="sessao-card__pauta">
            <strong>Descrição da Pauta:</strong> {sessao.pautaDescricao}
          </span>
        </div>
        
        <div className="sessao-card__actions">
          {sessao.status === 'ABERTA' && (
            <div className="sessao-card__vote-actions">
              <Button 
                variant="success" 
                size="sm"
                onClick={() => onVotar(sessao.id)}
              >
                Votar
              </Button>
            </div>
          )}
        </div>
      </div>

                {sessao.status === 'ENCERRADA' && (
                  <div className="pauta-card__resultado">
                    <Gauge
                      sim={sessao.resultado.sim}
                      nao={sessao.resultado.nao}
                    />
                  </div>
                )}
    </Card>
  );
};