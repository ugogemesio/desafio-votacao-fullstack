import React, { useMemo } from 'react';

interface GaugeProps {
    sim: number;
    nao: number;
    size?: number;
    strokeWidth?: number;
}

export const Gauge: React.FC<GaugeProps> = ({
    sim,
    nao,
    size = 120,
    strokeWidth = 10,
}) => {
    const total = sim + nao;

    const simPercentage = total > 0 ? (sim / total) * 100 : 0;
    const naoPercentage = total > 0 ? (nao / total) * 100 : 0;

    const radius = (size - strokeWidth) / 2;
    const circumference = 2 * Math.PI * radius;

    // comprimento em "pixels" do arco
    const simLen = (simPercentage / 100) * circumference;
    const naoLen = (naoPercentage / 100) * circumference;

    // offset para posicionar o arco de "não" logo após o arco de "sim"
    // rotacionamos -90deg para começar no topo, então deslocamento aqui posiciona corretamente
    const naoOffset = circumference - simLen; // posiciona o início do arco de "não" depois do fim do "sim"

    const majority = sim === nao ? 'EMPATE' : sim > nao ? 'SIM' : 'NAO';

    return (
        <div className="gauge-container" style={{ display: 'inline-flex', flexDirection: 'column', alignItems: 'center' }}>
            <svg width={size} height={size}>

                {/* Fundo (círculo base) */}
                <circle
                    cx={size / 2}
                    cy={size / 2}
                    r={radius}
                    stroke="#e9ecef"
                    strokeWidth={strokeWidth}
                    fill="none"
                />

                {/* SIM (verde) - desenhado primeiro */}
                <circle
                    cx={size / 2}
                    cy={size / 2}
                    r={radius}
                    stroke="#28a745"
                    strokeWidth={strokeWidth}
                    fill="none"
                    strokeLinecap="butt"

                    // arco visível = simLen, resto invisível
                    strokeDasharray={`${simLen} ${circumference - simLen}`}
                    // começamos no topo por causa da rotação; offset 0 faz começar no topo
                    strokeDashoffset={0}
                    transform={`rotate(-90 ${size / 2} ${size / 2})`}
                    className="gauge-progress-sim"
                />

                {/* NÃO (vermelho) - desenhado depois e posicionado logo após o SIM */}
                <circle
                    cx={size / 2}
                    cy={size / 2}
                    r={radius}
                    stroke="#dc3545"
                    strokeWidth={strokeWidth}
                    fill="none"
                    strokeLinecap="butt"

                    strokeDasharray={`${naoLen} ${circumference - naoLen}`}
                    // desloca o início do arco de "não" para começar onde o "sim" terminou
                    strokeDashoffset={naoOffset}
                    transform={`rotate(-90 ${size / 2} ${size / 2})`}
                    className="gauge-progress-nao"
                />

                {/* Percentual da maioria no centro */}
                <text
                    x="50%"
                    y="45%"
                    textAnchor="middle"
                    fontSize={20}
                    fontWeight="bold"
                    fill={majority === 'SIM' ? '#28a745' : majority === 'NAO' ? '#dc3545' : '#ffc107'}
                >
                    {total > 0 ? `${Math.round(Math.max(simPercentage, naoPercentage))}%` : '—'}
                </text>

                {/* Label */}
                <text
                    x="50%"
                    y="65%"
                    textAnchor="middle"
                    fontSize={12}
                    fill="#6c757d"
                >
                    {total === 0 ? 'Sem votos' : majority === 'EMPATE' ? 'Empate' : majority === 'SIM' ? 'Aprovada' : 'Rejeitada'}
                </text>
            </svg>

            {/* Legenda */}
            {total > 0 && (
                <div className="gauge-legend" style={{ marginTop: 6, display: 'flex', gap: 12, fontSize: 13 }}>
                    <span style={{ color: '#28a745' }}>Sim: {sim} ({Math.round(simPercentage)}%)</span>
                    <span style={{ color: '#dc3545' }}>Não: {nao} ({Math.round(naoPercentage)}%)</span>
                </div>
            )}

            <style>{`
        .gauge-progress-sim,
        .gauge-progress-nao {
          transition: stroke-dasharray 600ms ease, stroke-dashoffset 600ms ease;
        }
      `}</style>
        </div>
    );
};
