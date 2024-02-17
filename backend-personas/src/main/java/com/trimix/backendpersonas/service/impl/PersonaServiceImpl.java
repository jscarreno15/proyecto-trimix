package com.trimix.backendpersonas.service.impl;

import com.trimix.backendpersonas.dtos.PersonaDTO;
import com.trimix.backendpersonas.dtos.PersonaTablaDTO;
import com.trimix.backendpersonas.entity.PersonaEntity;
import com.trimix.backendpersonas.entity.TipoDocumentoEnum;
import com.trimix.backendpersonas.exception.PersonaNoEncontradaException;
import com.trimix.backendpersonas.repository.PersonaRepository;
import com.trimix.backendpersonas.service.PersonaService;
import com.trimix.backendpersonas.specification.PersonaSpecification;
import com.trimix.backendpersonas.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PersonaSpecification personaSpecification;

    @Override

    public PersonaDTO guardarPersona(PersonaDTO persona) {
        log.info(":::::::: SERVICIO - GUARDAR PERSONA ::::::::");
        PersonaEntity personaEntity = modelMapper.map(persona, PersonaEntity.class);
        personaEntity = personaRepository.save(personaEntity);
        return modelMapper.map(personaEntity, PersonaDTO.class);
    }

    @Override
    public PersonaDTO editarPersona(PersonaDTO persona) {
        log.info(":::::::: SERVICIO - EDITAR PERSONA ::::::::");
        Optional.ofNullable(persona.getId()).orElseThrow(() -> new RuntimeException("Campo ID requerido"));
        PersonaEntity personaEntity = modelMapper.map(persona, PersonaEntity.class);
        personaEntity = personaRepository.save(personaEntity);
        return modelMapper.map(personaEntity, PersonaDTO.class);
    }

    @Override
    public Boolean borrarPersona(Long idPersona) {
        log.info(":::::::: SERVICIO - BORRAR PERSONA ::::::::");
        PersonaEntity personaEntity = personaRepository.findById(idPersona).orElseThrow(() -> new PersonaNoEncontradaException(HttpStatus.BAD_REQUEST, idPersona.toString()));
        personaRepository.delete(personaEntity);
        return true;
    }

    @Override
    public Map<String, Object> listarPersonas(Integer paginaActual, Integer totalRegistros, TipoDocumentoEnum tipoDocumentoEnum, String nombre) {
        log.info(":::::::: SERVICIO - LISTAR PERSONAS ::::::::");
        PageRequest pageRequest = PageRequest.of(paginaActual,totalRegistros);
        Specification<PersonaEntity> specification = Specification
                .where(personaSpecification.filtroTipoDocumento(tipoDocumentoEnum)
                        .and(personaSpecification.filtroNombre(nombre)));
        Page<PersonaEntity> page = personaRepository.findAll(specification, pageRequest);
        List<PersonaTablaDTO> listaPersonas = page.getContent().stream().map(persona -> modelMapper.map(persona, PersonaTablaDTO.class)).collect(Collectors.toList());
        return Util.itemMapeoPaginado(listaPersonas, page.getNumber(),page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public PersonaDTO buscarPersonaPorId(Long id) {
        log.info(":::::::: SERVICIO - BUSCAR PERSONA POR ID::::::::");
        PersonaEntity personaEntity = personaRepository.findById(id).orElseThrow(() -> new PersonaNoEncontradaException(HttpStatus.BAD_REQUEST, id.toString()));
        return modelMapper.map(personaEntity,PersonaDTO.class);
    }
}
