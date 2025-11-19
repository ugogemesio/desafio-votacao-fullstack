import { useMutation, useQueryClient } from "@tanstack/react-query";
import { votoApi } from "../services/votoService";
import type { VotoCreateDTO } from "../types/voto";

export function useVotos() {
  const queryClient = useQueryClient();


  const votar = useMutation({
    mutationFn: (data: VotoCreateDTO) => votoApi.votar(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["votos"] });
      queryClient.invalidateQueries({ queryKey: ["sessoes"] });
      queryClient.invalidateQueries({ queryKey: ["pautas"] });
    },
  });

  return { votar };
}
