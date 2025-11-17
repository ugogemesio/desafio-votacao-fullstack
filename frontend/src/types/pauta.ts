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
}

export interface PautaResponseDTO {
  id: number;
  descricao: string;
  titulo: string;
  dataCriacao: string; 
}
