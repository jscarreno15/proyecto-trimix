import { Component, OnInit } from '@angular/core';
import { PersonaDTO } from '../dtos/PersonaDTO';
import { PersonaService } from '../services/persona.service';
import { Utils } from '../services/utils';
import { MatDialog, MatDialogRef} from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MatPaginatorIntl, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-personas',
  templateUrl: './personas.component.html',
  styleUrls: ['./personas.component.css']
})
export class PersonasComponent implements OnInit{
  displayedColumns: string[] = ['id', 'nombre', 'apellido', 'numero_documento', 'tipo_documento', 'fecha_nacimiento', 'editar', 'borrar'];
  personas : PersonaDTO[] = [];
  clickedRows = new Set<PersonaDTO>();
  paginaActual: number = 0;
  totalRegistros: number = 10;
  totalPaginas: number = 0;
  registrosPorPagina: number = 5;
  todosDocumentos: string = '';
  documentoFiltro: string= '';
  nombreFiltro: string = '';
  TiposDocumentos = Utils.TiposDocumentos;
  modalAbierto: boolean = false;

  constructor(private personaService: PersonaService,
              private router: Router,
              public dialog: MatDialog,
              private toastr: ToastrService,
              private paginatorIntl: MatPaginatorIntl){}

  ngOnInit(): void {
    this.cargarDatosTabla();
    this.paginatorIntl.itemsPerPageLabel = 'Personas por página';
  }

  cargarDatosTabla(){
    if(this.documentoFiltro === 'TODAS')
      this.documentoFiltro = '';
    this.personaService.listarPersonas(
      this.paginaActual,
      this.registrosPorPagina,
      this.documentoFiltro,
      this.nombreFiltro
    ).subscribe({
      next: (data: any) => {
        this.personas = data?.listaPersonas;
        this.totalRegistros = data?.totalRegistros;
        this.totalPaginas = data?.totalPaginas;
        this.paginaActual = data?.paginaActual+1;
      },
      error: (err) => {
        console.error('Error al obtener los datos de tabla ', err);
        this.toastr.error('Error al obtener los datos de tabla.');
      }
    })
  }

  editarPersona(idPersona: PersonaDTO) {
    this.router.navigate(['/editar', idPersona]);
  }

  borrar(idPersona: number) {
    this.personaService.borrarPersona(idPersona).subscribe({
      next: (data: boolean) => {
        this.toastr.success("La persona fue eliminada correctamente.");
        this.paginaActual = 0;
        this.registrosPorPagina = 5;
        this.cargarDatosTabla();
      },
      error: (err) => {
        console.error('Error al borrar registro ', err);
        this.toastr.error('Error al borrar registro.');
      }
    })
  }

  aplicarFiltro(){
    this.paginaActual = 0;
    this.registrosPorPagina = 5;
    this.cargarDatosTabla();
  }

  redirectToCrear() {
    this.router.navigate(['/crear']);
  }

  onPageChange(event: PageEvent) {
    console.log("progas" , event);
    this.paginaActual = event.pageIndex;
    this.registrosPorPagina = event.pageSize;
    this.cargarDatosTabla();
  }

  openDialog(idPersona: number, enterAnimationDuration: string, exitAnimationDuration: string): void {
    const dialogRef = this.dialog.open(DialogAnimations, {
      width: '500px',
      height: '250px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  
    dialogRef.afterClosed().subscribe((result) => {
      if (result === true)
        this.borrar(idPersona);
    });
  }
}


@Component({
  selector: 'dialog-animations-example-dialog',
  templateUrl: 'personas.dialog.html',
  styleUrls: ['./personas.component.css']
})
export class  DialogAnimations {
  constructor(public dialogRef: MatDialogRef<DialogAnimations>) {}

  onAccept(): void {
    this.dialogRef.close(true);
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}