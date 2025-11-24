export interface SessaoCreateDTO {
  duracaoMinutos: number;
}

export interface SessaoResponseDTO {
  id: number;
  abertura: string;     
  fechamento: string;   
  duracaoMinutos: number;
  pautaId: number;
  status:string;
}
export interface SessaoPautaDTO {
  id: number;
  abertura: string;        
  fechamento: string;      
  duracaoMinutos: number;
  pautaId: number;
  status: string
  pautaTitulo: string;
  pautaDescricao: string;
  resultado: Resultado;
}

export interface Resultado {
  sim: number;
  nao: number;
  status: ResultadoStatus;
}

type ResultadoStatus = 'APROVADO' | 'REJEITADO' | 'EMPATE' ;

