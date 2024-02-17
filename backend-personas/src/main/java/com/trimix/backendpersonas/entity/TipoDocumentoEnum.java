package com.trimix.backendpersonas.entity;

import lombok.Getter;

@Getter
public enum TipoDocumentoEnum {
    DNI("DNI"),
    PASAPORTE("PASAPORTE"),
    CEDULA("CEDULA");

    private String descripcion;

    TipoDocumentoEnum(String descripcion){this.descripcion = descripcion; }

    @Override
    public String toString(){return descripcion; }
}
