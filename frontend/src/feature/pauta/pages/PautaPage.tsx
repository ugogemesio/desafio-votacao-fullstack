import React, { useState } from 'react';
import { PautaResumo } from './PautaResumo';
import PautaList from './PautaList';
import { SessaoModal } from '../../sessao/pages/SessaoModal';
import { PautaModal } from './PautaCriarModal';
import { usePautas } from '../hooks/usePautas';
import { useModal } from '../../atoms/hookModal';
import { useSessoes } from '../../sessao/hooks/useSessoes';
import '../pages/PautaPage.scss';

export const PautaPage: React.FC = () => {
  const { lista, criar } = usePautas();
  const { criar: criarSessao } = useSessoes();
  const modalSessao = useModal();
  const modalPauta = useModal();
  
  const [pautaSelecionada, setPautaSelecionada] = useState<{
    id: number;
    titulo: string;
  } | null>(null);

  const pautas = lista.data || [];
  const loading = lista.isLoading;

  const stats = pautas.reduce((acc, p) => {
    if (p.status === 'ABERTA') acc.abertas++;
    if (p.status === 'DEFINIDA') acc.fechadas++;
    if (p.status === 'VOTANDO') acc.votando++;
    return acc;
  }, { abertas: 0, fechadas: 0, votando: 0 });

  const handleCriarPauta = () => {
    modalPauta.open();
  };

  const handleCriarPautaSubmit = (data: { titulo: string; descricao: string }) => {
    criar.mutate(data, {
      onSuccess: () => {
        alert("Pauta Criada")
        modalPauta.close();
      },
      onError: (error) => {
        console.error('Erro ao criar pauta:', error);
        alert('Erro ao criar pauta. Tente novamente.');
      }
    });
  };

  const handleResultados = (pautaId: number) => {
    console.log('Ver resultados da pauta:', pautaId);
  };

  const handleAbrirSessaoPauta = (pautaId: number) => {
    const pauta = pautas.find(p => p.id === pautaId);
    if (pauta) {
      setPautaSelecionada({
        id: pautaId,
        titulo: pauta.titulo
      });
      modalSessao.open();
    }
  };

  const handleCriarSessao = (duracaoMinutos: number) => {
    if (pautaSelecionada) {
      criarSessao.mutate(
        { 
          idPauta: pautaSelecionada.id, 
          data: { duracaoMinutos } 
        },
        {
          onSuccess: () => {
            modalSessao.close();
            setPautaSelecionada(null);
            alert('Sessão criada com sucesso!');
          },
          onError: (error) => {
            console.error('Erro ao criar sessão:', error);
            alert('Erro ao criar sessão. Tente novamente.');
          }
        }
      );
    }
  };

  if (loading) {
    return (
      <div className="home-page">
        <div className="loading-container">
          <div className="loading-spinner"></div>
          <p>Carregando pautas...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="home-page">
      <main className="home-main">
        <PautaResumo
          pautasAbertas={stats.abertas}
          pautasFechadas={stats.fechadas}
          proximaSessao={stats.votando}
          onCriarPauta={handleCriarPauta}
        />

        <section className="pautas-section">
          <div className="pautas-container">
            <h2 className="pautas-title">Pautas</h2>
            
            <PautaList
              pautas={pautas}
              onResultados={handleResultados}
              onAbrirSessao={handleAbrirSessaoPauta}
            />

            {pautas.length === 0 && (
              <div className="empty-state">
                <p>Nenhuma pauta encontrada.</p>
                <button 
                  className="hero__action-btn hero__action-btn--primary"
                  onClick={handleCriarPauta}
                >
                  Criar Primeira Pauta
                </button>
              </div>
            )}
          </div>
        </section>
      </main>

      {/* Modal para criar sessão */}
      <SessaoModal
        isOpen={modalSessao.isOpen}
        onClose={modalSessao.close}
        onCriarSessao={handleCriarSessao}
        pautaId={pautaSelecionada?.id || 0}
        pautaTitulo={pautaSelecionada?.titulo}
        isLoading={criarSessao.isPending}
      />

      {/* Modal para criar pauta */}
      <PautaModal
        isOpen={modalPauta.isOpen}
        onClose={modalPauta.close}
        onCriarPauta={handleCriarPautaSubmit}
        isLoading={criar.isPending}
      />
    </div>
  );
};