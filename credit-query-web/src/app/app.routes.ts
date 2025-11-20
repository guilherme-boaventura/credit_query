import { Routes } from '@angular/router';
import { NotFoundComponent } from './core/pages/not-found/not-found.component';
import { ConsultaCreditoComponent } from './modules/consulta-credito/components/consulta-credito/consulta-credito.component';

export const routes: Routes = [
  { path: '', component: ConsultaCreditoComponent },
  { path: '**', component: NotFoundComponent }
];
