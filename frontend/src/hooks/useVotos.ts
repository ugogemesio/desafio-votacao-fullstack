import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { votoApi } from "../services/votoService";
import type { VotoCreateDTO, VotoResponseDTO } from "../types/voto";

export function useVotos() {
  const queryClient = useQueryClient();

  const buscarPorId = (id: number) =>
    useQuery<VotoResponseDTO>({
      queryKey: ["votos", id],
      queryFn: () => votoApi.buscarPorId(id),
      enabled: !!id,
    });

  const votar = useMutation({
    mutationFn: (data: VotoCreateDTO) => votoApi.votar(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["votos"] });
      queryClient.invalidateQueries({ queryKey: ["sessoes"] });
      queryClient.invalidateQueries({ queryKey: ["pautas"] });
    },
  });

  return { buscarPorId, votar };
}
