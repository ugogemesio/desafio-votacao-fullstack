export interface VotoCreateDTO {
  idAssociado: number;
  idSessao: number;
  valor: boolean;
}

export interface VotoResponseDTO {
  id: number;
  idAssociado: number;
  idSessao: number;
  valor: boolean;
}
