import React from 'react';
import { ResumoSection } from '../../molecules/ResumoSection';
import { ResumoCard } from '../../atoms/ResumoCard';

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
    <ResumoSection
      titulo="Sessões de Votação"
      subtitulo="Gerencie e acompanhe as sessões de votação"
      actionLabel="Criar Nova Sessão"
      onAction={onCriarSessao}
    >
      <ResumoCard
        icon="📊"
        value={sessoesAbertas}
        label="Sessões Abertas"
      />

      <ResumoCard
        icon="✅"
        value={sessoesFechadas}
        label="Sessões Encerradas"
      />

      <ResumoCard
        icon="📈"
        value={totalSessoes}
        label="Total de Sessões"
      />
    </ResumoSection>
  );
};