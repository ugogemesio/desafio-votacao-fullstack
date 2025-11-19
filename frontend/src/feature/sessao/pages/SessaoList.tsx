import React from 'react';
import { useSessoes } from '../hooks/useSessoes';
import type { SessaoPautaDTO } from '../types/sessao';
import './SessaoList.scss';
import { SessaoCard } from './SessaoCard';

interface SessaoListProps {
  onVotar: (sessaoId: number) => void;
  onResultados: (sessaoId: number) => void;
}

const SessaoList: React.FC<SessaoListProps> = ({ 
  onVotar, 
  onResultados 
}) => {
  const { listaPorPautas} = useSessoes();

  const sessoes = listaPorPautas.data || [];
  const loading = listaPorPautas.isLoading;

  if (loading) {
    return (
      <div className="loading-container">
        <div className="loading-spinner"></div>
        <p>Carregando sessões...</p>
      </div>
    );
  }

  if (sessoes.length === 0) {
    return (
      <div className="sessao-list__empty">
        <p>Nenhuma sessão encontrada.</p>
      </div>
    );
  }

  return (
    <section className="sessao-list">
      <div className="sessao-list__header">
        <h2 className="sessao-list__title">Sessões de Votação</h2>
        <span className="sessao-list__count">
          {sessoes.length} sessão{sessoes.length !== 1 ? 's' : ''} encontrada{sessoes.length !== 1 ? 's' : ''}
        </span>
      </div>
      
      <div className="sessao-list__grid">
        {sessoes.map((sessao: SessaoPautaDTO) => (
          <SessaoCard
            key={sessao.id}
            sessao={sessao}
            onVotar={() => onVotar(sessao.id)}
            onResultados={() => onResultados(sessao.id)}
          />
        ))}
      </div>
    </section>
  );
};

export default SessaoList;