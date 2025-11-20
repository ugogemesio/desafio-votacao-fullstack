import React, { useState } from 'react';
import { Modal } from '../../molecules/Modal';
import { Button } from '../../atoms/Button';
import { SessaoModal } from '../../sessao/pages/SessaoModal';
import { usePautas } from '../../pauta/hooks/usePautas';
import { useSessoes } from '../../sessao/hooks/useSessoes';
import { useModal } from '../../atoms/hookModal';
import './PautasModal.scss';

interface PautasParaSessaoModal {
  isOpen: boolean;
  onClose: () => void;
}

export const PautasParaSessaoModal: React.FC<PautasParaSessaoModal> = ({
  isOpen,
  onClose
}) => {
  const { listaPautaAberta } = usePautas();
  const { criar } = useSessoes();
  const sessaoModal = useModal();

  const [pautaSelecionada, setPautaSelecionada] = useState<{
    id: number;
    titulo: string;
  } | null>(null);


  console.log('Lista de pautas abertas:', listaPautaAberta.data);
  console.log('Loading:', listaPautaAberta.isLoading);
  console.log('Error:', listaPautaAberta.error);

  const pautasAbertas = listaPautaAberta.data || [];
  const loading = listaPautaAberta.isLoading;

  const handleAbrirSessao = (pautaId: number, pautaTitulo: string) => {
    setPautaSelecionada({ id: pautaId, titulo: pautaTitulo });
    sessaoModal.open();
  };

  const handleCriarSessao = (duracaoMinutos: number) => {
    if (pautaSelecionada) {
      criar.mutate(
        {
          idPauta: pautaSelecionada.id,
          data: { duracaoMinutos }
        },
        {
          onSuccess: () => {
            sessaoModal.close();
            onClose();
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

  const handleClose = () => {
    setPautaSelecionada(null);
    onClose();
  };

  return (
    <>
      <Modal
        isOpen={isOpen}
        onClose={handleClose}
        title="Selecionar Pauta para Sessão"
        size="lg"
      >
        <div className="pautas-sessao-modal">
          {loading ? (
            <div className="pautas-sessao-modal__loading">
              <div className="loading-spinner"></div>
              <p>Carregando pautas...</p>
            </div>
          ) : listaPautaAberta.error ? (
            <div className="pautas-sessao-modal__error">
              <p>Erro ao carregar pautas: {listaPautaAberta.error.message}</p>
            </div>
          ) : pautasAbertas.length === 0 ? (
            <div className="pautas-sessao-modal__empty">
              <p>Nenhuma pauta disponível para criar sessão.</p>
              <p className="pautas-sessao-modal__empty-subtitle">
                Todas as pautas já possuem sessões em andamento ou já foram votadas.
              </p>
            </div>
          ) : (
            <>
              <div className="pautas-sessao-modal__header">
                <p className="pautas-sessao-modal__info">
                  Selecione uma pauta para abrir uma sessão de votação:
                </p>
                <div className="pautas-sessao-modal__count">
                  {pautasAbertas.length} pauta{pautasAbertas.length !== 1 ? 's' : ''} disponível{pautasAbertas.length !== 1 ? 's' : ''}
                </div>
              </div>

              <div className="pautas-sessao-modal__list">
                {pautasAbertas.map((pauta) => (
                  <div key={pauta.id} className="pauta-item">
                    <div className="pauta-item__content">
                      <h4 className="pauta-item__titulo">{pauta.titulo}</h4>
                      <p className="pauta-item__descricao">{pauta.descricao}</p>
                      <div className="pauta-item__info">
                        <span className="pauta-item__id">ID: {pauta.id}</span>
                        <span className="pauta-item__data">
                          Criada em: {new Date(pauta.dataCriacao).toLocaleString("pt-BR")}
                        </span>
                      </div>
                    </div>
                    <div className="pauta-item__actions">
                      <Button
                        variant="primary"
                        size="sm"
                        onClick={() => handleAbrirSessao(pauta.id, pauta.titulo)}
                        disabled={criar.isPending}
                      >
                        Abrir Sessão
                      </Button>
                    </div>
                  </div>
                ))}
              </div>
            </>
          )}
        </div>
      </Modal>

      <SessaoModal
        isOpen={sessaoModal.isOpen}
        onClose={sessaoModal.close}
        onCriarSessao={handleCriarSessao}
        pautaId={pautaSelecionada?.id || 0}
        pautaTitulo={pautaSelecionada?.titulo}
        isLoading={criar.isPending}
      />
    </>
  );
};  