// components/layout/ResumoSection.tsx
import React from "react";
import "./ResumoSection.scss";

interface ResumoSectionProps {
  titulo: string;
  subtitulo?: string;
  children: React.ReactNode;
  actionLabel: string;
  onAction: () => void;
}

export const ResumoSection: React.FC<ResumoSectionProps> = ({
  titulo,
  subtitulo,
  children,
  actionLabel,
  onAction
}) => {
  return (
    <section className="resumo-section">
      <div className="resumo-section__header">
        <h1>{titulo}</h1>
        {subtitulo && <p>{subtitulo}</p>}
      </div>

      <div className="resumo-section__grid">
        {children}
      </div>

      <div className="resumo-section__actions">
        <button className="btn-primary" onClick={onAction}>
          {actionLabel}
        </button>
      </div>
    </section>
  );
};
