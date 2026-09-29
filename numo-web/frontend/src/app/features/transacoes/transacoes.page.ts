import { CurrencyPipe, DatePipe } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import { Categoria, ResumoFinanceiro, TipoTransacao, Transacao } from '../../core/models';
import { mensagemDeErro, NumoApiService } from '../../core/numo-api.service';

@Component({
  selector: 'app-transacoes-page',
  imports: [ReactiveFormsModule, CurrencyPipe, DatePipe, RouterLink],
  templateUrl: './transacoes.page.html',
})
export class TransacoesPage implements OnInit {
  private readonly api = inject(NumoApiService);
  private readonly fb = inject(NonNullableFormBuilder);

  protected readonly transacoes = signal<Transacao[]>([]);
  protected readonly categorias = signal<Categoria[]>([]);
  protected readonly resumo = signal<ResumoFinanceiro>({ totalReceitas: 0, totalDespesas: 0, saldo: 0 });
  protected readonly erro = signal<string | null>(null);
  protected readonly salvando = signal(false);
  protected readonly carregado = signal(false);

  protected readonly form = this.fb.group({
    tipo: this.fb.control<TipoTransacao>('DESPESA'),
    descricao: ['', [Validators.required, Validators.maxLength(120)]],
    valor: this.fb.control<number | null>(null, [Validators.required, Validators.min(0.01)]),
    data: [hoje(), Validators.required],
    categoriaId: ['', Validators.required],
  });

  private readonly tipoAtual = signal<TipoTransacao>('DESPESA');

  /** Só mostra categorias compatíveis com o tipo escolhido (mesma regra do domínio). */
  protected readonly categoriasDoTipo = computed(() =>
    this.categorias().filter((c) => c.tipo === this.tipoAtual()),
  );

  private readonly nomes = computed(
    () => new Map(this.categorias().map((c) => [c.id, c.nome] as const)),
  );

  ngOnInit(): void {
    this.form.controls.tipo.valueChanges.subscribe((tipo) => {
      this.tipoAtual.set(tipo);
      this.form.controls.categoriaId.setValue('');
    });
    this.carregar();
  }

  protected nomeCategoria(id: string): string {
    return this.nomes().get(id) ?? '—';
  }

  protected salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const v = this.form.getRawValue();
    this.salvando.set(true);
    this.erro.set(null);
    this.api.registrarTransacao({ ...v, valor: Number(v.valor) }).subscribe({
      next: () => {
        this.form.reset({ tipo: v.tipo, descricao: '', valor: null, data: v.data, categoriaId: v.categoriaId });
        this.salvando.set(false);
        this.carregar();
      },
      error: (e) => {
        this.erro.set(mensagemDeErro(e));
        this.salvando.set(false);
      },
    });
  }

  protected remover(t: Transacao): void {
    this.api.removerTransacao(t.id).subscribe({
      next: () => this.carregar(),
      error: (e) => this.erro.set(mensagemDeErro(e)),
    });
  }

  private carregar(): void {
    forkJoin({
      categorias: this.api.listarCategorias(),
      transacoes: this.api.listarTransacoes(),
      resumo: this.api.resumo(),
    }).subscribe({
      next: ({ categorias, transacoes, resumo }) => {
        this.categorias.set(categorias);
        this.transacoes.set(transacoes);
        this.resumo.set(resumo);
        this.carregado.set(true);
      },
      error: (e) => {
        this.erro.set(mensagemDeErro(e));
        this.carregado.set(true);
      },
    });
  }
}

function hoje(): string {
  const d = new Date();
  const off = d.getTimezoneOffset() * 60000;
  return new Date(d.getTime() - off).toISOString().slice(0, 10);
}
