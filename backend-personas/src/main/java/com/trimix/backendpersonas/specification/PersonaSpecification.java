package com.trimix.backendpersonas.specification;

import com.trimix.backendpersonas.entity.PersonaEntity;
import com.trimix.backendpersonas.entity.TipoDocumentoEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PersonaSpecification {

    public Specification<PersonaEntity> filtroTipoDocumento(TipoDocumentoEnum tipoDocumentoEnum){
        return (personaEntity, query , criteriaBuilder) ->
                Objects.isNull(tipoDocumentoEnum) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(personaEntity.get("tipoDocumento"), tipoDocumentoEnum);
    }

    public Specification<PersonaEntity> filtroNombre(String nombre){
        return (personaEntity, query , criteriaBuilder) ->
                nombre.isBlank() ? criteriaBuilder.conjunction() : criteriaBuilder.like(personaEntity.get("nombre"),'%'+ nombre+'%');
    }
}
