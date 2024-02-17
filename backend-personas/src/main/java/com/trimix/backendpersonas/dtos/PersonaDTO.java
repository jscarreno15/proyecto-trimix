package com.trimix.backendpersonas.dtos;

import com.trimix.backendpersonas.entity.TipoDocumentoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonaDTO {
    private Long id;
    @NotNull(message = "persona.valid.nombre")
    @NotBlank(message = "persona.valid.nombre")
    private String nombre;
    @NotNull(message = "persona.valid.apellido")
    @NotBlank(message = "persona.valid.apellido")
    private String apellido;
    @NotNull(message = "persona.valid.fechaNacimiento")
    private Date fechaNacimiento;
    @NotNull(message = "persona.valid.numeroDocumento")
    private Long numeroDocumento;
    @NotNull(message = "persona.valid.tipoDocumento")
    private TipoDocumentoEnum tipoDocumento;
}
