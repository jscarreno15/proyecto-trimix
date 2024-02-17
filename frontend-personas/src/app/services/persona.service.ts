import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { PersonaDTO } from '../dtos/PersonaDTO';

@Injectable({
  providedIn: 'root'
})
export class PersonaService {

  constructor(private httpClient: HttpClient) { }

  private baseUrl: string = environment.baseUrl;

  guardarPersona(data: PersonaDTO): Observable<PersonaDTO>{
    return this.httpClient.post<PersonaDTO>(this.baseUrl + 'personas', data);
  }

  editarPersona(data: PersonaDTO): Observable<PersonaDTO>{
    return this.httpClient.put<PersonaDTO>(this.baseUrl + 'personas', data);
  }

  listarPersonas(paginaActual:number, totalRegistros:number, tipoDocumento?:string, nombre?:string):Observable<any>{
    return this.httpClient.get<any>(this.baseUrl+'personas/listar?paginaActual='+ paginaActual + '&totalRegistros=' + totalRegistros + '&tipoDocumentoEnum=' + tipoDocumento + '&nombre=' + nombre);
  }

  buscarPersona(idPersona:any):Observable<PersonaDTO>{
    return this.httpClient.get<PersonaDTO>(this.baseUrl+'personas/'+idPersona);
  }

  borrarPersona(idPersona:number):Observable<boolean>{
    return this.httpClient.delete<boolean>(this.baseUrl+'personas/'+idPersona);
  }
}
