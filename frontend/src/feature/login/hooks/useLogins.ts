import { useMutation } from "@tanstack/react-query";
import { loginApi } from "../services/loginService";
import type { LoginRequestDTO, LoginResponseDTO } from "../types/login";

export function useLogin() {
  const login = useMutation<LoginResponseDTO, unknown, LoginRequestDTO>({
    mutationFn: (data: LoginRequestDTO) => loginApi.login(data),
  });

  return { login };
}
