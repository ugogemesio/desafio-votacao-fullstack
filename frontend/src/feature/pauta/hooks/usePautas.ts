import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { pautaApi } from "../services/pautaService";
import type {
  PautaCreateDTO,
  PautaPutDTO,
  PautaPatchDTO,
  PautaResponseDTO
} from "../types/pauta";

export function usePautas() {
  const queryClient = useQueryClient();

  const lista = useQuery<PautaResponseDTO[]>({
    queryKey: ["pautas", "todas"],
    queryFn: pautaApi.listar,
    staleTime: 60_000,
  });

  const listaPautaAberta = useQuery<PautaResponseDTO[]>({
    queryKey: ["pautas", "status", "ABERTA"], 
    queryFn: () => pautaApi.listarPorStatus("ABERTA"), 
    staleTime: 60_000,
  });

  const listaPautaDefinida = useQuery<PautaResponseDTO[]>({
    queryKey: ["pautas", "status", "DEFINIDA"],
    queryFn: () => pautaApi.listarPorStatus("DEFINIDA"), 
    staleTime: 60_000,
  });

  const listaPautaVotando = useQuery<PautaResponseDTO[]>({
    queryKey: ["pautas", "status", "VOTANDO"], 
    queryFn: () => pautaApi.listarPorStatus("VOTANDO"), 
    staleTime: 60_000,
  });

  const buscarPorId = (id: number) =>
    useQuery<PautaResponseDTO>({
      queryKey: ["pautas", "detalhe", id], 
      queryFn: () => pautaApi.buscarPorId(id),
      enabled: !!id,
    });

  const criar = useMutation({
    mutationFn: (data: PautaCreateDTO) => pautaApi.criar(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["pautas"] });
    },
  });

  const atualizar = useMutation({
    mutationFn: (params: { id: number; data: PautaPutDTO }) =>
      pautaApi.atualizar(params.id, params.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["pautas"] });
    },
  });

  const atualizarParcial = useMutation({
    mutationFn: (params: { id: number; data: PautaPatchDTO }) =>
      pautaApi.atualizarParcial(params.id, params.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["pautas"] });
    },
  });

  const deletar = useMutation({
    mutationFn: (id: number) => pautaApi.deletar(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["pautas"] });
    },
  });

  return {
    lista,
    buscarPorId,
    criar,
    atualizar,
    atualizarParcial,
    deletar,
    listaPautaAberta,
    listaPautaDefinida,
    listaPautaVotando
  };
}