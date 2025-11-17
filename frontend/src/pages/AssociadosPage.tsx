import { useAssociados } from "../hooks/useAssociados";

const AssociadosPage = () => {
  const { lista, criar } = useAssociados();

  if (lista.isLoading) return <p>Carregando...</p>;
  if (lista.error) return <p>Erro ao buscar associados</p>;

  if (!Array.isArray(lista.data)) {
    console.error('Expected array but got:', lista.data);
    return <p>Dados não estão no formato esperado</p>;
  }

  console.log(lista.data)

  return (
    <div>
      <h2>Associados</h2>

      <ul>
        {lista.data.map((a) => (
          <li key={a.id}>
            {a.nome} — {a.cpf}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AssociadosPage;