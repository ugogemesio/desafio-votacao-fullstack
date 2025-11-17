import { http } from "../api/httpClient";
import type {
    LoginRequestDTO,
    LoginResponseDTO
} from "../types/login";
export const loginApi = {

  async login(data: LoginRequestDTO): Promise<LoginResponseDTO> {
    const res = await http.post<LoginResponseDTO>("/login", data);
    return res.data;
  },
}