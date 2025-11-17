import { useState } from "react";
import { useLogin } from "../hooks/useLogins";

const LoginPage = () => {
  const { login } = useLogin();
  const [cpf, setCpf] = useState("");
  const [result, setResult] = useState<string | null>(null);

  const handleLogin = () => {
    login.mutate(
      { cpf },
      {
        onSuccess: (response) => {
          setResult(`Status: ${response.status}`);
        },
        onError: () => {
          setResult("CPF inválido ou não autorizado a votar");
        },
      }
    );
  };

  return (
    <div>
      <h2>Login de Associado</h2>
      <input
        type="text"
        placeholder="Digite o CPF"
        value={cpf}
        onChange={(e) => setCpf(e.target.value)}
      />
      <button onClick={handleLogin}>Entrar</button>

      {result && <p>{result}</p>}
    </div>
  );
};

export default LoginPage;
