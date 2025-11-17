import { useState } from 'react';
import './App.css'
import AssociadoPage from "./pages/AssociadosPage"
import LoginPage from './pages/LoginsPage';

function App() {
  const [cpf, setCpf] = useState("");
  const [error, setError] = useState("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value.replace(/\D/g, "");
    setCpf(value);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (cpf.length !== 11) {
      setError("CPF deve ter 11 números");
      return;
    }

    setError("");
    alert(`CPF enviado: ${cpf}`);
  }

  return (
    <>
      <div style={{ maxWidth: "300px", margin: "0 auto", padding: "20px" }}>
        <h2>Login com CPF</h2>
        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: "10px" }}>
            <label htmlFor="cpf">CPF:</label>
            <input
              id="cpf"
              type="text"
              value={cpf}
              onChange={handleChange}
              maxLength={11}
              placeholder="Digite seu CPF"
              style={{ width: "100%", padding: "8px", boxSizing: "border-box" }}
            />
          </div>
          {error && <p style={{ color: "red" }}>{error}</p>}
          <button type="submit" style={{ width: "100%", padding: "10px" }}>
            Entrar
          </button>
        </form>
      </div>
      <AssociadoPage />
      <LoginPage></LoginPage>
    </>
  )
}

export default App;
