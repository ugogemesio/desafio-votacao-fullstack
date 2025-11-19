import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { sessaoApi } from "../services/sessaoService";
import type { SessaoCreateDTO, SessaoPautaDTO, SessaoResponseDTO } from "../types/sessao";

export function useSessoes() {
  const queryClient = useQueryClient();


  const listaPorPautas = useQuery<SessaoPautaDTO[]>({
    queryKey: ["sessoes"],
    queryFn: sessaoApi.listarPorPautas,
    staleTime: 60_000,
  });

  const buscarPorId = (id: number) =>
    useQuery<SessaoResponseDTO>({
      queryKey: ["sessoes", id],
      queryFn: () => sessaoApi.buscarPorId(id),
      enabled: !!id,
    });

  const criar = useMutation({
    mutationFn: (params: { idPauta: number; data: SessaoCreateDTO }) =>
      sessaoApi.criar(params.idPauta, params.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["sessoes"] });
      queryClient.invalidateQueries({ queryKey: ["pautas"] });
    },
    
  });
  

  return {
    buscarPorId,
    criar,
    listaPorPautas
  };
}
