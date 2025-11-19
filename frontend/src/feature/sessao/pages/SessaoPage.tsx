import React, { useState } from 'react';
import { SessaoResumo } from './SessaoResumo';
import SessaoList from './SessaoList';
import { VotoModal } from '../../voto/pages/VotoModal';
import { useSessoes } from '../hooks/useSessoes';
import { useVotos } from '../../voto/hooks/useVotos';
import { useModal } from '../../atoms/hookModal';
import './SessaoPage.scss';
import { PautasParaSessaoModal } from '../../pauta/pages/PautasModal'; 

export const SessaoPage: React.FC = () => {
  const { listaPorPautas } = useSessoes();
  const { votar } = useVotos();
  const votoModal = useModal();
  const pautasSessaoModal = useModal();

  const [sessaoSelecionada, setSessaoSelecionada] = useState<number | null>(null);
  const [erroVoto, setErroVoto] = useState<string>('');

  const sessoes = listaPorPautas.data || [];
  const loading = listaPorPautas.isLoading;

  const stats = sessoes.reduce((acc, s) => {
    if (s.status === 'ABERTA') acc.abertas++;
    if (s.status === 'ENCERRADA') acc.fechadas++;
    return acc;
  }, { abertas: 0, fechadas: 0 });

  const handleCriarSessao = () => {
    console.log('Abrindo modal de pautas...'); 
    pautasSessaoModal.open();
  };

  const handleVotar = (sessaoId: number) => {
    setSessaoSelecionada(sessaoId);
    setErroVoto('');
    votoModal.open();
  };

  const handleResultados = (sessaoId: number) => {
    console.log('Ver resultados da sessão:', sessaoId);
  };

  const handleEnviarVoto = (voto: boolean, cpfAssociado: string) => {
    if (sessaoSelecionada) {
      setErroVoto('');
      
      votar.mutate(
        {
          cpfAssociado,
          idSessao: sessaoSelecionada,
          valor: voto
        },
        {
          onSuccess: () => {
            votoModal.close();
            setSessaoSelecionada(null);
            setErroVoto('');
            alert('Voto registrado com sucesso!');
          },
          onError: (error: any) => {
            console.error('Erro ao votar:', error);
            
            let errorMessage = 'Erro ao registrar voto. Tente novamente.';
            
            if (error?.response?.data?.message) {
              errorMessage = error.response.data.message;
            } else if (error?.message) {
              errorMessage = error.message;
            }
            
            setErroVoto(errorMessage);
          }
        }
      );
    }
  };

  const handleCloseVotoModal = () => {
    setErroVoto('');
    votoModal.close();
  };

  if (loading) {
    return (
      <div className="sessoes-page">
        <div className="loading-container">
          <div className="loading-spinner"></div>
          <p>Carregando sessões...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="sessoes-page">
      <main className="sessoes-main">
        <SessaoResumo
          sessoesAbertas={stats.abertas}
          sessoesFechadas={stats.fechadas}
          totalSessoes={sessoes.length}
          onCriarSessao={handleCriarSessao}
        />

        <section className="sessoes-section">
          <div className="sessoes-container">
            <h2 className="sessoes-title">Sessões de Votação</h2>

            <SessaoList
              onVotar={handleVotar}
              onResultados={handleResultados}
            />

            {sessoes.length === 0 && (
              <div className="empty-state">
                <p>Nenhuma sessão de votação encontrada.</p>
                <p className="empty-state__subtitle">
                  Para criar uma sessão, primeiro crie uma pauta e depois abra uma sessão para ela.
                </p>
                <button
                  className="hero__action-btn hero__action-btn--primary"
                  onClick={handleCriarSessao}
                >
                  Criar Sessão a partir de Pautas
                </button>
              </div>
            )}
          </div>
        </section>
      </main>

      {/* Modal de votação */}
      <VotoModal
        isOpen={votoModal.isOpen}
        onClose={handleCloseVotoModal}
        onVotar={handleEnviarVoto}
        sessaoId={sessaoSelecionada || 0}
        isLoading={votar.isPending}
        erro={erroVoto}
      />


      <PautasParaSessaoModal
        isOpen={pautasSessaoModal.isOpen}
        onClose={pautasSessaoModal.close}
      />
    </div>
  );
};