import { Component, inject, OnInit, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Categoria, TipoTransacao } from '../../core/models';
import { mensagemDeErro, NumoApiService } from '../../core/numo-api.service';

@Component({
  selector: 'app-categorias-page',
  imports: [ReactiveFormsModule],
  templateUrl: './categorias.page.html',
})
export class CategoriasPage implements OnInit {
  private readonly api = inject(NumoApiService);
  private readonly fb = inject(NonNullableFormBuilder);

  protected readonly categorias = signal<Categoria[]>([]);
  protected readonly erro = signal<string | null>(null);
  protected readonly salvando = signal(false);

  protected readonly form = this.fb.group({
    nome: ['', [Validators.required, Validators.maxLength(50)]],
    tipo: this.fb.control<TipoTransacao>('DESPESA'),
  });

  ngOnInit(): void {
    this.carregar();
  }

  protected salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.salvando.set(true);
    this.erro.set(null);
    this.api.criarCategoria(this.form.getRawValue()).subscribe({
      next: () => {
        this.form.reset({ nome: '', tipo: this.form.controls.tipo.value });
        this.salvando.set(false);
        this.carregar();
      },
      error: (e) => {
        this.erro.set(mensagemDeErro(e));
        this.salvando.set(false);
      },
    });
  }

  private carregar(): void {
    this.api.listarCategorias().subscribe({
      next: (lista) => this.categorias.set(lista),
      error: (e) => this.erro.set(mensagemDeErro(e)),
    });
  }
}
