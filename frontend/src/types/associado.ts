export interface AssociadoResponseDTO {
  id: number;
  nome: string;
  cpf: string;
}

export interface AssociadoCreateDTO {
  nome: string;
  cpf: string;
}

export interface AssociadoPutDTO {
  nome: string;
  cpf: string;
}

export interface AssociadoPatchDTO {
  nome?: string;
  cpf?: string;
}
