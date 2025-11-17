import { http } from "../api/httpClient";
import type {
  PautaCreateDTO,
  PautaPutDTO,
  PautaPatchDTO,
  PautaResponseDTO
} from "../types/pauta";

export const pautaApi = {
  async listar(): Promise<PautaResponseDTO[]> {
    const res = await http.get<PautaResponseDTO[]>("/pautas");
    return res.data;
  },

  async buscarPorId(id: number): Promise<PautaResponseDTO> {
    const res = await http.get<PautaResponseDTO>(`/pautas/${id}`);
    return res.data;
  },

  async criar(data: PautaCreateDTO): Promise<PautaResponseDTO> {
    const res = await http.post<PautaResponseDTO>("/pautas", data);
    return res.data;
  },

  async atualizar(id: number, data: PautaPutDTO): Promise<PautaResponseDTO> {
    const res = await http.put<PautaResponseDTO>(`/pautas/${id}`, data);
    return res.data;
  },

  async atualizarParcial(id: number, data: PautaPatchDTO): Promise<PautaResponseDTO> {
    const res = await http.patch<PautaResponseDTO>(`/pautas/${id}`, data);
    return res.data;
  },

  async deletar(id: number): Promise<void> {
    await http.delete(`/pautas/${id}`);
  }
};
