import React from 'react';
import { Card } from '../../atoms/Card';
import './PautaResumo.scss';

interface PautaResumoProps {
  pautasAbertas: number;
  pautasFechadas: number;
  proximaSessao: number;
  onCriarPauta: () => void;
  
}

export const PautaResumo: React.FC<PautaResumoProps> = ({
  pautasAbertas,
  pautasFechadas,
  proximaSessao,
  onCriarPauta
}) => {
  
  const resumoItems = [
    { icon: '📄', value: pautasAbertas, label: 'Pautas Abertas' },
    { icon: '✅', value: pautasFechadas, label: 'Pautas Encerradas' },
    { icon: '🕒', value: proximaSessao, label: 'Pautas em Votação' },
  ];

  return (
    <section className="hero">
      <div className="hero__container">
        <div className="hero__resumo">
          {resumoItems.map(item => (
            <Card key={item.label} className="resumo-card" padding="lg">
              <div className="resumo-card__item">
                <div className="resumo-card__icon">{item.icon}</div>
                <div className="resumo-card__content">
                  <div className="resumo-card__value">{item.value}</div>
                  <div className="resumo-card__label">{item.label}</div>
                </div>
              </div>
            </Card>
          ))}
        </div>

        <div className="hero__actions">
          <button 
            className="hero__action-btn hero__action-btn--primary"
            onClick={onCriarPauta}
          >
            Criar Nova Pauta
          </button>

        </div>
      </div>
    </section>
  );
};
