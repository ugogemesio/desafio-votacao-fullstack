import { useState, type FormEvent } from "react";
import { useNavigate } from "react-router-dom"; 
import { useLogin } from "../hooks/useLogins";
import { useAssociados } from "../../associado/hooks/useAssociados";
import ModalCadastrarAssociado from "../../associado/pages/ModalCadastrarAssociado";
import { Button } from "../../atoms/Button";
import { Input } from "../../atoms/Input";
import { Card } from "../../atoms/Card";
import { validarCPF, aplicarMascaraCPF } from "../../../utils/cpfUtils";
import { associadoApi } from "../../associado/services/associadoService";

import './LoginPage.scss';

const LoginPage = () => {
  const { login } = useLogin();
  const { criar } = useAssociados();
  const navigate = useNavigate();

  const [cpf, setCpf] = useState("");
  const [feedback, setFeedback] = useState<{ message: string; type: 'success' | 'error' } | null>(null);
  const [modalAberto, setModalAberto] = useState(false);
  const [isCheckingCpf, setIsCheckingCpf] = useState(false);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();

    const cpfNumerico = cpf.replace(/\D/g, '');

    if (!validarCPF(cpfNumerico)) {
      setFeedback({ message: "CPF inválido", type: 'error' });
      return;
    }

    setIsCheckingCpf(true);

    try {
      const associadoExistente = await verificarCpfExistente(cpfNumerico);

      if (associadoExistente) {
        localStorage.setItem('cpfAssociado', cpfNumerico);

        setFeedback({ message: "Login realizado com sucesso!", type: 'success' });
        setTimeout(() => {
          navigate('/pautas');
        }, 1000);
        return;
      }


      await verificarAptidaoVotacao(cpfNumerico);

    } catch (error) {
      setFeedback({ message: "Erro ao processar a solicitação", type: 'error' });
    } finally {
      setIsCheckingCpf(false);
    }
  };

  const verificarCpfExistente = async (cpfNumerico: string): Promise<boolean> => {
    try {
      await associadoApi.buscarPorCpf(cpfNumerico);
      return true;
    } catch (error) {
      return false;
    }
  };

  const verificarAptidaoVotacao = (cpfNumerico: string): Promise<void> => {
    return new Promise((resolve, reject) => {
      login.mutate(
        { cpf: cpfNumerico },
        {
          onSuccess: ({ status }) => {

            if (status === "ABLE_TO_VOTE") {
              localStorage.setItem('cpfAssociado', cpfNumerico);
              setModalAberto(true);
              setFeedback(null);
            } else {
              setFeedback({ message: "Não autorizado a votar", type: 'error' });
            }
            resolve();
          },
          onError: () => {
            setFeedback({ message: "CPF não encontrado na base de votação", type: 'error' });
            reject();
          },
        }
      );
    });
  };

  const salvarAssociado = (nome: string) => {
    const cpfNumerico = cpf.replace(/\D/g, '');

    criar.mutate(
      { nome, cpf: cpfNumerico },
      {
        onSuccess: () => {
          setModalAberto(false);
          setCpf("");
          setFeedback({ message: "Cadastro realizado com sucesso!", type: 'success' });
          setTimeout(() => {
            navigate('/pautas');
          }, 1000);
        },
        onError: () => {
          setFeedback({ message: "Erro ao salvar associado", type: 'error' });
        },
      }
    );
  };

  const handleCpfChange = (value: string) => {
    setCpf(aplicarMascaraCPF(value));
    if (feedback) setFeedback(null);
  };

  const isLoading = login.isPending || isCheckingCpf;

  return (
    <div className="login-page">
      <div className="login-container">
        <Card className="login-card" padding="lg">
          <div className="login-header">
            <h1 className="login-title">Sistema de Votação</h1>
            <p className="login-subtitle">Identificação do Associado</p>
          </div>

          <form onSubmit={handleSubmit} className="login-form">
            <Input
              type="text"
              label="CPF"
              placeholder="Digite seu CPF"
              value={cpf}
              onChange={handleCpfChange}
              disabled={isLoading}
              autoFocus
            />

            <Button
              type="submit"
              variant="primary"
              fullWidth
              loading={isLoading}
              disabled={!cpf.replace(/\D/g, '')}
            >
              {isLoading ? "Verificando..." : "Entrar"}
            </Button>
          </form>

          {feedback && (
            <div className={`feedback feedback--${feedback.type}`}>
              {feedback.message}
            </div>
          )}

          <div className="login-info">
            <p className="info-text">
              <strong>Como funciona:</strong>
            </p>
            <ul className="info-list">
              <li>Digite seu CPF para verificar se você está apto a votar</li>
              <li>Se for seu primeiro acesso, você será cadastrado automaticamente</li>
              <li>Após a verificação, você poderá participar das votações</li>
            </ul>
          </div>
        </Card>
      </div>

      {modalAberto && (
        <ModalCadastrarAssociado
          onClose={() => setModalAberto(false)}
          onSalvar={salvarAssociado}
          loading={criar.isPending}
        />
      )}
    </div>
  );
};

export default LoginPage;