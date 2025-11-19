import { useState, type FormEvent } from "react";
import { useVotos } from "../hooks/useVotos";

const VotarPage = () => {
  const { votar } = useVotos();

  
  const [idSessao, setIdSessao] = useState<number | undefined>();
  const [valor, setValor] = useState(true);
  const [cpfAssociado, setCpfAssociado] = useState<string | undefined>(localStorage.getItem('cpfAssociado') ?? undefined);  

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    if (!cpfAssociado || !idSessao) return;

    votar.mutate({ cpfAssociado, idSessao, valor });
  };

  return (
    <main>
      <h2>Registrar Voto</h2>

      <form onSubmit={handleSubmit}>
        <label htmlFor="associado">ID do Associado</label>
        <input
          id="associado"
          type="number"
          min={1}
          value={cpfAssociado ?? ""}
        />

        <label htmlFor="sessao">ID da Sessão</label>
        <input
          id="sessao"
          type="number"
          min={1}
          value={idSessao ?? ""}
          onChange={(e) => setIdSessao(Number(e.target.value))}
        />

        <label htmlFor="valor">Voto</label>
        <select
          id="valor"
          value={valor ? "SIM" : "NAO"}
          onChange={(e) => setValor(e.target.value === "SIM")}
        >
          <option value="SIM">Sim</option>
          <option value="NAO">Não</option>
        </select>

        <button type="submit" disabled={votar.isPending}>
          {votar.isPending ? "Enviando..." : "Votar"}
        </button>
      </form>

      {votar.isSuccess && <p>Voto computado com sucesso!</p>}
      {votar.isError && <p>Erro ao registrar voto.</p>}
    </main>
  );
};

export default VotarPage;
