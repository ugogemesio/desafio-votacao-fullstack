import React from 'react';
import { usePautas } from '../hooks/usePautas';
import { PautaCard } from './PautaCard';
import type { PautaResponseDTO } from '../types/pauta';
import './PautaList.scss';

interface PautaListProps {
  pautas: PautaResponseDTO[];
  onResultados: (pautaId: number) => void;
  onAbrirSessao: (pautaId: number) => void;
}

const PautaList: React.FC<PautaListProps> = ({ 
  onResultados, 
  onAbrirSessao 
}) => {
  const { lista } = usePautas();

  const pautas = lista.data || [];
  const loading = lista.isLoading;

  if (loading) {
    return (
      <div className="loading-container">
        <div className="loading-spinner"></div>
        <p>Carregando pautas...</p>
      </div>
    );
  }

  if (pautas.length === 0) {
    return null; 
  }

  return (
    <section className="pauta-list">
      <div className="pauta-list__grid">
        {pautas.map((pauta: PautaResponseDTO) => (
          <PautaCard 
            key={pauta.id}
            pauta={(pauta)}
            onResultados={() => onResultados(pauta.id)}
            onAbrirSessao={() => onAbrirSessao(pauta.id)}
          />
        ))}
      </div>
    </section>
  );
};

export default PautaList;