export interface PautaCreateDTO {
  descricao: string;
  titulo: string;
}

export interface PautaPutDTO {
  descricao: string;
  titulo: string;
}

export interface PautaPatchDTO {
  descricao?: string;
  titulo?: string;
  status?: string
}

export interface PautaResponseDTO {
  id: number;
  descricao: string;
  titulo: string;
  dataCriacao: string; 
  resultado: Resultado
  status: string;
}

export interface Resultado {
  sim: number;
  nao: number;
  status: string;
}