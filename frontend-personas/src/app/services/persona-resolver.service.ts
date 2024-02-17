import { Injectable } from '@angular/core';
import { PersonaService } from './persona.service';
import { ActivatedRouteSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { PersonaDTO } from '../dtos/PersonaDTO';

@Injectable({
  providedIn: 'root'
})
export class PersonaResolverService {

  constructor(private personaService: PersonaService) { }

  resolve(route: ActivatedRouteSnapshot):Observable<PersonaDTO>{
    const id = route.paramMap.get('idPersona');
    return this.personaService.buscarPersona(id);
  }
}
