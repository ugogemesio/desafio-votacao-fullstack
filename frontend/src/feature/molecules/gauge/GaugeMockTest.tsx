import React from 'react';
import { Gauge } from './Gauge';

export const GaugeMockTest: React.FC = () => {
  const cenarios = [
    { sim: 0, nao: 0, titulo: 'Sem votos' },
    { sim: 1, nao: 9, titulo: 'Rejeição forte' },
    { sim: 3, nao: 7, titulo: 'Rejeição leve' },
    { sim: 5, nao: 5, titulo: 'Empate' },
    { sim: 7, nao: 3, titulo: 'Aprovação leve' },
    { sim: 12, nao: 2, titulo: 'Aprovação forte' },
    { sim: 50, nao: 5, titulo: 'Quase unânime' }
  ];

  return (
    <div style={{ padding: 24 }}>
      <h2>Mock - Teste de Gauges</h2>

      <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fill, minmax(160px, 1fr))',
        gap: '24px',
        marginTop: '24px'
      }}>
        {cenarios.map((c, index) => (
          <div
            key={index}
            style={{
              padding: 16,
              border: '1px solid #ddd',
              borderRadius: 8,
              textAlign: 'center'
            }}
          >
            <strong style={{ marginBottom: 8, display: 'block' }}>
              {c.titulo}
            </strong>

            <Gauge sim={c.sim} nao={c.nao} size={120} />

            <div style={{ marginTop: 8, fontSize: 13, color: '#555' }}>
              Sim: {c.sim} | Não: {c.nao}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
export default GaugeMockTest;
