import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import {
  Categoria,
  NovaCategoria,
  NovaTransacao,
  ResumoFinanceiro,
  Transacao,
} from './models';

@Injectable({ providedIn: 'root' })
export class NumoApiService {
  private readonly http = inject(HttpClient);
  private readonly base = '/api';

  listarCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.base}/categorias`);
  }

  criarCategoria(body: NovaCategoria): Observable<Categoria> {
    return this.http.post<Categoria>(`${this.base}/categorias`, body);
  }

  listarTransacoes(): Observable<Transacao[]> {
    return this.http.get<Transacao[]>(`${this.base}/transacoes`);
  }

  registrarTransacao(body: NovaTransacao): Observable<Transacao> {
    return this.http.post<Transacao>(`${this.base}/transacoes`, body);
  }

  removerTransacao(id: string): Observable<void> {
    return this.http.delete<void>(`${this.base}/transacoes/${id}`);
  }

  resumo(): Observable<ResumoFinanceiro> {
    return this.http.get<ResumoFinanceiro>(`${this.base}/transacoes/resumo`);
  }
}

/** Extrai a mensagem do ProblemDetail que a API devolve. */
export function mensagemDeErro(err: unknown): string {
  if (err instanceof HttpErrorResponse) {
    if (err.status === 0) return 'Não foi possível falar com a API. Ela está rodando na porta 8080?';
    return err.error?.detail ?? 'Algo deu errado ao salvar.';
  }
  return 'Algo deu errado ao salvar.';
}
