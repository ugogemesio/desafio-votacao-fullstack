import { http } from "../api/httpClient";
import type {
  SessaoCreateDTO,
  SessaoResponseDTO
} from "../types/sessao";

export const sessaoApi = {
  async listar(): Promise<SessaoResponseDTO[]> {
    const res = await http.get<SessaoResponseDTO[]>("/sessoes");
    return res.data;
  },

  async buscarPorId(id: number): Promise<SessaoResponseDTO> {
    const res = await http.get<SessaoResponseDTO>(`/sessoes/${id}`);
    return res.data;
  },

  async criar(idPauta: number, data: SessaoCreateDTO): Promise<SessaoResponseDTO> {
    const res = await http.post<SessaoResponseDTO>(
      `/pautas/${idPauta}/sessoes`,
      data
    );
    return res.data;
  }
};
