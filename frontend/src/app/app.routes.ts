import { Routes } from '@angular/router';
import { CategoriasPage } from './features/categorias/categorias.page';
import { TransacoesPage } from './features/transacoes/transacoes.page';

export const routes: Routes = [
  { path: '', component: TransacoesPage, title: 'Numo · Extrato' },
  { path: 'categorias', component: CategoriasPage, title: 'Numo · Categorias' },
  { path: '**', redirectTo: '' },
];
