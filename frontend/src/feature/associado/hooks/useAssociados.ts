import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { associadoApi } from "../services/associadoService";
import type {
  AssociadoCreateDTO,
  AssociadoPutDTO,
  AssociadoResponseDTO
} from "../types/associado";

export function useAssociados({ id, cpf }: { id?: number; cpf?: string } = {}) {
  const queryClient = useQueryClient();

  const lista = useQuery<AssociadoResponseDTO[]>({
    queryKey: ["associados"],
    queryFn: associadoApi.listar,
    enabled: id === undefined && !cpf,
    staleTime: 60_000,
  });

  const buscarPorId = useQuery<AssociadoResponseDTO>({
    queryKey: ["associados", "id", id],
    queryFn: () => associadoApi.buscarPorId(id!),
    enabled: id !== undefined,
    staleTime: 60_000,
  });

  const buscarPorCpf = useQuery<AssociadoResponseDTO>({
    queryKey: ["associados", "cpf", cpf],
    queryFn: () => {
      if (!cpf) {
        throw new Error("CPF não fornecido");
      }
      return associadoApi.buscarPorCpf(cpf);
    },
    enabled: !!cpf && cpf.length === 11, 
    retry: false,
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

  return {
    lista,
    buscarPorId,
    buscarPorCpf,
    criar,
    atualizar,
  };
}