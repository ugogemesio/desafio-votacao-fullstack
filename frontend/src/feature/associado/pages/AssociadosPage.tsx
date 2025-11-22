import GaugeMockTest from "../../molecules/gauge/GaugeMockTest";
import { useAssociados } from "../hooks/useAssociados";
useAssociados

const AssociadosPage = () => {
  const { lista } = useAssociados();

  if (lista.isLoading) return <p>Carregando...</p>;
  if (lista.isError) return <p>Erro ao carregar associados.</p>;

  const associados = lista.data ?? [];

  return (
    <main>
      <h2>Associados</h2>

      {associados.length ? (
        <ul>
          {associados.map((a) => (
            <li key={a.id}>
              {a.nome} — {a.cpf} - {a.id}
                    <GaugeMockTest></GaugeMockTest>

              <button>Deletar</button>
              <button>Editar</button>
            </li>
          ))}
        </ul>
      ) : (
        <p>Nenhum associado cadastrado.</p>
      )}
    </main>
  );
};

export default AssociadosPage;
