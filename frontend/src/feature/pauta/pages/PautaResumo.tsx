import React from 'react';
import { ResumoSection } from '../../molecules/ResumoSection';
import { ResumoCard } from '../../atoms/ResumoCard';

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
  return (
    <ResumoSection
      titulo="Pautas"
      subtitulo="Resumo geral das pautas cadastradas"
      actionLabel="Criar Nova Pauta"
      onAction={onCriarPauta}
    >
      <ResumoCard
        icon="📄"
        value={pautasAbertas}
        label="Pautas Abertas"
      />

      <ResumoCard
        icon="✅"
        value={pautasFechadas}
        label="Pautas Encerradas"
      />

      <ResumoCard
        icon="🕒"
        value={proximaSessao}
        label="Pautas em Votação"
      />
    </ResumoSection>
  );
};
