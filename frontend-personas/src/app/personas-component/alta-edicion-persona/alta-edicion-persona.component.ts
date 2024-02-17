import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PersonaDTO } from 'src/app/dtos/PersonaDTO';
import { PersonaService } from 'src/app/services/persona.service';
import { Utils } from 'src/app/services/utils';

@Component({
  selector: 'app-alta-edicion-persona',
  templateUrl: './alta-edicion-persona.component.html',
  styleUrls: ['./alta-edicion-persona.component.css']
})
export class AltaEdicionPersonaComponent implements OnInit{
  formPersonas!: FormGroup;
  persona: PersonaDTO = new PersonaDTO;
  todosDocumentos: string = '';
  TiposDocumentos = Utils.TiposDocumentos;

  constructor(private formBuilder: FormBuilder,
              private personaService: PersonaService,
              private toastr: ToastrService,
              private router: Router,
              private route: ActivatedRoute){}

  ngOnInit(): void {
    let personaEdicion = this.route.snapshot.data['persona'];
    console.log(personaEdicion);
    personaEdicion === undefined ? this.cargarForm() : this.mapearObjetoPersona(personaEdicion);
  }

  mapearObjetoPersona(persona: any){
    const fechaFormateada = this.formatearFecha(persona?.fechaNacimiento);
    this.persona = {
      perId: persona?.id,
      perNombre: persona?.nombre,
      perApellido: persona?.apellido,
      perFechaNacimiento: fechaFormateada,
      perNumeroDocumento: persona?.numeroDocumento,
      perTipoDocumento: persona?.tipoDocumento
    };
    this.cargarForm();
  }

  cargarForm(){
    this.formPersonas = this.formBuilder.group(
      {
        id: new FormControl({value: this.persona?.perId, disabled: false}),
        nombre: new FormControl({value: this.persona?.perNombre, disabled: false}, Validators.required),
        apellido: new FormControl({value: this.persona?.perApellido, disabled: false}, Validators.required),
        fechaNacimiento: new FormControl({value: this.persona?.perFechaNacimiento, disabled: false}, Validators.required),
        numeroDocumento: new FormControl({value: this.persona?.perNumeroDocumento, disabled: false}, Validators.required),
        tipoDocumento: new FormControl({value: this.persona?.perTipoDocumento, disabled: false}, Validators.required),
      }
    )
  }

  guardarPersona(){
    let body = this.formPersonas.value as PersonaDTO;
    this.personaService.guardarPersona(body).subscribe({
      next: (data:PersonaDTO) => {
        this.persona = data;
        this.success();
      },
      error: (err) => {
        console.error('Error al crear la persona: ', err);
        this.toastr.error("Error al intentar guardar persona.");
      }
    })
  }

  cargarDatosPersona(idPersona: number){
    this.personaService.buscarPersona(idPersona).subscribe({
      next: (data:any) => {
        this.persona = {
          perId: data.id,
          perNombre:  data.nombre,
          perApellido:  data.apellido,
          perFechaNacimiento:  data.fechaNacimiento,
          perNumeroDocumento:  data.numeroDocumento,
          perTipoDocumento: data.tipoDocumento 
        } 
        this.cargarForm();
      },
      error: (err) => {
        console.error('Error al cargar datos de la persona: ', err);
        this.toastr.error("Error al cargar datos de la persona.");
      }
    })
  }

  validation( campo:string ){
    return Utils.validationForm( campo, this.formPersonas);
  }

  success(){
    this.toastr.success('Persona creada correctamente.');
    this.router.navigate(['/inicio']);
  }

  volver(){
    this.router.navigate(['/inicio']);
  }

  formatearFecha(cadenaFecha: string): string {
    // Convertir la cadena de fecha en un objeto Date
    const fecha = new Date(cadenaFecha);

    // Extraer el año, mes y día
    const anio = fecha.getFullYear();
    const mes = (fecha.getMonth() + 1).toString().padStart(2, '0'); // Sumamos 1 porque los meses van de 0 a 11
    const dia = fecha.getDate().toString().padStart(2, '0');

    // Formar la nueva cadena en el formato "yyyy-mm-dd"
    return `${anio}-${mes}-${dia}`;
}


}

class Accordion {
  private accordionItems: HTMLElement[];

  constructor() {
    this.accordionItems = Array.from(document.querySelectorAll('.accordion-item'));
    this.init();
  }

  private init() {
    this.accordionItems.forEach(item => {
      const button = item.querySelector('.accordion-button');
      const collapse = item.querySelector('.accordion-collapse');

      if (button && collapse) {
        button.addEventListener('click', () => {
          const isCollapsed = collapse.classList.contains('show');
          this.closeAll();

          if (!isCollapsed) {
            collapse.classList.add('show');
          }
        });
      }
    });
  }

  private closeAll() {
    this.accordionItems.forEach(item => {
      const collapse = item.querySelector('.accordion-collapse');
      if (collapse) {
        collapse.classList.remove('show');
      }
    });
  }
}

document.addEventListener('DOMContentLoaded', () => {
  new Accordion();
});
