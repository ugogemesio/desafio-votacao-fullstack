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
  status?: PautaStatus
}

export interface PautaResponseDTO {
  id: number;
  descricao: string;
  titulo: string;
  dataCriacao: string; 
  resultado: Resultado
  status: PautaStatus;
}

export interface Resultado {
  sim: number;
  nao: number;
  status: PautaStatus;
}

export type PautaStatus = 'ABERTA' | 'VOTANDO' | 'DEFINIDA';
