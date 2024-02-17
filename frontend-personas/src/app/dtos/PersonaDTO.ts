export class PersonaDTO {
  perId!: number;
  perNombre!: string;
  perApellido!: string;
  perFechaNacimiento!: string;
  perNumeroDocumento!: string;
  perTipoDocumento!: TipoDocumentoDTO;
}

export enum TipoDocumentoDTO{
  DNI= "Documento de indentidad",
  PASAPORTE = 'Pasaporte',
  CEDULA = 'Cèdula'
}