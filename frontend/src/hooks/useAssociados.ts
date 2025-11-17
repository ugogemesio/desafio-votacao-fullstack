import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { associadoApi } from "../services/associadoService";
import type {
  AssociadoCreateDTO,
  AssociadoPutDTO,
  AssociadoResponseDTO
} from "../types/associado";

export function useAssociados() {
  const queryClient = useQueryClient();

  const lista = useQuery<AssociadoResponseDTO[]>({
    queryKey: ["associados"],
    queryFn: associadoApi.listar,
    staleTime: 60_000, 
  });

  const criar = useMutation({
    mutationFn: (data: AssociadoCreateDTO) => associadoApi.criar(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["associados"] });
    },
  });

  const atualizar = useMutation({
    mutationFn: (params: { id: number; data: AssociadoPutDTO }) =>
      associadoApi.atualizar(params.id, params.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["associados"] });
    },
  });

  return { lista, criar, atualizar };
}
