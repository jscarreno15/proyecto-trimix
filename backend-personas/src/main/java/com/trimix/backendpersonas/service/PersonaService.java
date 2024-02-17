package com.trimix.backendpersonas.service;

import com.trimix.backendpersonas.dtos.PersonaDTO;
import com.trimix.backendpersonas.entity.TipoDocumentoEnum;

import java.util.Map;

public interface PersonaService {
    PersonaDTO guardarPersona(PersonaDTO persona);
    PersonaDTO editarPersona(PersonaDTO persona);
    Boolean borrarPersona(Long idPersona);
    Map<String, Object> listarPersonas(Integer paginaActual, Integer totalRegistros, TipoDocumentoEnum tipoDocumentoEnum, String nombre);
    PersonaDTO buscarPersonaPorId(Long id);
}
