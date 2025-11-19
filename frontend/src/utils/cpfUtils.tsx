export const validarCPF = (cpf: string): boolean => {
  cpf = cpf.replace(/\D/g, "");
  return cpf.length === 11;
};

export const aplicarMascaraCPF = (cpf: string): string => {
  return cpf;

};
