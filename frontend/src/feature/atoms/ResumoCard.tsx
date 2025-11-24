interface ResumoCardProps {
  icon: string;
  value: number;
  label: string;
}

export const ResumoCard: React.FC<ResumoCardProps> = ({ icon, value, label }) => {
  return (
    <div className="resumo-card">
      <div className="resumo-card__icon">{icon}</div>
      <div>
        <h3>{value}</h3>
        <span>{label}</span>
      </div>
    </div>
  );
};
