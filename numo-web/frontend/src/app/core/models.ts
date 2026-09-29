export type TipoTransacao = 'RECEITA' | 'DESPESA';

export interface Categoria {
  id: string;
  nome: string;
  tipo: TipoTransacao;
}

export interface Transacao {
  id: string;
  descricao: string;
  valor: number;
  tipo: TipoTransacao;
  data: string;
  categoriaId: string;
}

export interface ResumoFinanceiro {
  totalReceitas: number;
  totalDespesas: number;
  saldo: number;
}

export type NovaCategoria = Omit<Categoria, 'id'>;
export type NovaTransacao = Omit<Transacao, 'id'>;
