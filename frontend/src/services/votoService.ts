import { http } from "../api/httpClient";
import type {
  VotoCreateDTO,
  VotoResponseDTO
} from "../types/voto";

export const votoApi = {
  async votar(data: VotoCreateDTO): Promise<VotoResponseDTO> {
    const res = await http.post<VotoResponseDTO>("/votos", data);
    return res.data;
  },

  async buscarPorId(id: number): Promise<VotoResponseDTO> {
    const res = await http.get<VotoResponseDTO>(`/votos/${id}`);
    return res.data;
  }
};
