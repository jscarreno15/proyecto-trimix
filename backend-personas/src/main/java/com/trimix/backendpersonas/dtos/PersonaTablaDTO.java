package com.trimix.backendpersonas.dtos;
import com.trimix.backendpersonas.entity.TipoDocumentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaTablaDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private Long numeroDocumento;
    private TipoDocumentoEnum tipoDocumento;
}
