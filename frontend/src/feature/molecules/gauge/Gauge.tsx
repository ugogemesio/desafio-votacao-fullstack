import React, { useEffect, useState } from 'react';

interface GaugeProps {
    sim: number;
    nao: number;
    size?: number;
}

export const Gauge: React.FC<GaugeProps> = ({ sim, nao, size = 120 }) => {
    const total = sim + nao;
    const percentage = total > 0 ? (sim / total) * 100 : 0;

    const strokeWidth = 10;
    const radius = (size - strokeWidth) / 2;
    const circumference = 2 * Math.PI * radius;

    const [offset, setOffset] = useState(circumference);

    useEffect(() => {
        const progress = circumference - (percentage / 100) * circumference;
        setOffset(progress);
    }, [percentage, circumference]);

    const getGaugeColor = () => {
        if (total === 0) return '#adb5bd';
        if (percentage > 60) return '#28a745';
        if (percentage < 40) return '#dc3545';
        return '#ffc107';
    };

    const getGaugeLabel = () => {
        if (total === 0) return 'Sem votos';
        if (percentage > 60) return 'Aprovada';
        if (percentage < 40) return 'Rejeitada';
        return 'Empate';
    };

    return (
        <div className="gauge-container">
            <svg width={size} height={size}>
                {/* Fundo */}
                <circle
                    cx={size / 2}
                    cy={size / 2}
                    r={radius}
                    stroke="#e9ecef"
                    strokeWidth={strokeWidth}
                    fill="none"
                />

                {/* Progresso */}
                <circle
                    cx={size / 2}
                    cy={size / 2}
                    r={radius}
                    stroke={getGaugeColor()}
                    strokeWidth={strokeWidth}
                    fill="none"
                    strokeDasharray={circumference}
                    strokeDashoffset={offset}
                    strokeLinecap="round"
                    transform={`rotate(-90 ${size / 2} ${size / 2})`}
                    className="gauge-progress"
                />

                {/* Percentual */}
                <text
                    x="50%"
                    y="45%"
                    textAnchor="middle"
                    fontSize="20"
                    fontWeight="bold"
                    fill={getGaugeColor()}
                >
                    {total > 0 ? `${Math.round(percentage)}%` : '—'}
                </text>

                {/* Label */}
                <text
                    x="50%"
                    y="65%"
                    textAnchor="middle"
                    fontSize="12"
                    fill="#6c757d"
                >
                    {getGaugeLabel()}
                </text>

            </svg>
            {total > 0 && (
                <text
                    x="50%"
                    y="72%"
                    textAnchor="middle"
                    fontSize="11"
                    fill="#495057"
                >
                    Sim: {sim} | Não: {nao}
                </text>
            )}

        </div>
    );
};
