import React from 'react';
import './SessaoResumo.scss';

interface SessaoResumoProps {
  sessoesAbertas: number;
  sessoesFechadas: number;
  totalSessoes: number;
  onCriarSessao: () => void;
}

export const SessaoResumo: React.FC<SessaoResumoProps> = ({
  sessoesAbertas,
  sessoesFechadas,
  totalSessoes,
  onCriarSessao
}) => {
  return (
    <section className="sessao-resumo">
      <div className="sessao-resumo__header">
        <h1 className="sessao-resumo__title">Sessões de Votação</h1>
        <p className="sessao-resumo__subtitle">
          Gerencie e acompanhe as sessões de votação em andamento
        </p>
      </div>

      <div className="sessao-resumo__stats">
        <div className="stat-card">
          <div className="stat-card__icon stat-card__icon--aberta">
            <span>📊</span>
          </div>
          <div className="stat-card__content">
            <h3 className="stat-card__value">{sessoesAbertas}</h3>
            <p className="stat-card__label">Sessões Abertas</p>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-card__icon stat-card__icon--fechada">
            <span>✅</span>
          </div>
          <div className="stat-card__content">
            <h3 className="stat-card__value">{sessoesFechadas}</h3>
            <p className="stat-card__label">Sessões Encerradas</p>
          </div>
        </div>


        <div className="stat-card">
          <div className="stat-card__icon stat-card__icon--total">
            <span>📈</span>
          </div>
          <div className="stat-card__content">
            <h3 className="stat-card__value">{totalSessoes}</h3>
            <p className="stat-card__label">Total de Sessões</p>
          </div>
        </div>
      </div>

      <div className="sessao-resumo__actions">
        <button 
          className="sessao-resumo__action-btn sessao-resumo__action-btn--primary"
          onClick={onCriarSessao}
        >
          Criar Nova Sessão
        </button>
      </div>
    </section>
  );
};