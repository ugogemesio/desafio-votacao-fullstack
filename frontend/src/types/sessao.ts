export interface SessaoCreateDTO {
  duracaoMinutos: number;
}

export interface SessaoResponseDTO {
  id: number;
  abertura: string;     
  fechamento: string;   
  duracaoMinutos: number;
  pautaId: number;
}
