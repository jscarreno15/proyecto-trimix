import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PersonasComponent } from './personas-component/personas.component';
import { AltaEdicionPersonaComponent } from './personas-component/alta-edicion-persona/alta-edicion-persona.component';
import { PersonaResolverService } from './services/persona-resolver.service';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch:'full' },
  { path: 'inicio', component: PersonasComponent },
  { path: 'editar/:idPersona', component: AltaEdicionPersonaComponent, resolve: { persona: PersonaResolverService}},
  { path: 'crear', component: AltaEdicionPersonaComponent},
  { path: '',
    component:PersonasComponent, 
    children: [ 
    {path: '', redirectTo: '/persona' , pathMatch: 'full'}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
