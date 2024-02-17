import { FormGroup } from "@angular/forms";
import { TipoDocumentoDTO } from "../dtos/PersonaDTO";

export class Utils {
  public static TiposDocumentos = Object.entries(TipoDocumentoDTO)
    .filter(([key, value]) => isNaN(Number(value)))
    .map(([key, value]) => ({ nombre: value, valor: key }));

  private static stringIsNotNumber(value: any): boolean {
    return isNaN(Number(value));
  }

  static validationForm( campo:string, formGroup:FormGroup ){
    return formGroup.controls[campo].errors &&
            formGroup.controls[campo].touched
  }
}
