import { http } from "../api/httpClient";
import type {
  AssociadoCreateDTO,
  AssociadoPutDTO,
  AssociadoPatchDTO,
  AssociadoResponseDTO
} from "../types/associado";

export const associadoApi = {
  async listar(): Promise<AssociadoResponseDTO[]> {
    const res = await http.get<AssociadoResponseDTO[]>("/associados");
    return res.data;
  },

  async buscarPorId(id: number): Promise<AssociadoResponseDTO> {
    const res = await http.get<AssociadoResponseDTO>(`/associados/${id}`);
    return res.data;
  },

  async criar(data: AssociadoCreateDTO): Promise<AssociadoResponseDTO> {
    const res = await http.post<AssociadoResponseDTO>("/associados", data);
    return res.data;
  },

  async atualizar(id: number, data: AssociadoPutDTO): Promise<AssociadoResponseDTO> {
    const res = await http.put<AssociadoResponseDTO>(`/associados/${id}`, data);
    return res.data;
  },

  async atualizarParcial(id: number, data: AssociadoPatchDTO): Promise<AssociadoResponseDTO> {
    const res = await http.patch<AssociadoResponseDTO>(`/associados/${id}`, data);
    return res.data;
  },

  async deletar(id: number): Promise<void> {
    await http.delete(`/associados/${id}`);
  }
};
