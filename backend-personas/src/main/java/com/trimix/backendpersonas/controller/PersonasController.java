package com.trimix.backendpersonas.controller;

import com.trimix.backendpersonas.dtos.PersonaDTO;
import com.trimix.backendpersonas.entity.TipoDocumentoEnum;
import com.trimix.backendpersonas.service.PersonaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("api/personas")
@Slf4j
@Api(tags = "Controlador Personas")
@CrossOrigin(origins = "*")
public class PersonasController {

    @Autowired
    private PersonaService personaService;


    /**
     * Método para crear persona.
     * @param persona DTO del objeto persona.
     * @return la persona creada con su respectivo ID.
     */
    @PostMapping("")
    @ApiOperation("Endpoint para crear persona")
    public ResponseEntity<PersonaDTO> crearPersona(@Valid @RequestBody PersonaDTO persona){
        log.info(":::::::: ENDPOINT CREAR PERSONA ::::::::");
        PersonaDTO personaDTO = personaService.guardarPersona(persona);
        return ResponseEntity.status(HttpStatus.OK).body(personaDTO);
    }

    /**
     * Método para editar persona.
     * @param persona DTO del objeto persona.
     * @return la persona editada con su respectivo ID.
     */
    @PutMapping("")
    @ApiOperation("Endpoint para editar persona")
    public ResponseEntity<PersonaDTO> editarPersona(@RequestBody PersonaDTO persona){
        log.info(":::::::: ENDPOINT EDITAR PERSONA ::::::::");
        PersonaDTO personaDTO = personaService.editarPersona(persona);
        return ResponseEntity.status(HttpStatus.OK).body(personaDTO);
    }

    /**
     * Método para listar personas paginadas y filtros.
     * @param paginaActual pagina actual del paginado.
     * @param totalRegistros cantidad de resultados a mostrar por pagina.
     * @param tipoDocumentoEnum tipo documento para aplicar filtro.
     * @param nombre nombre de persona para aplicar filtro.
     * @return listado de personas.
     */
    @GetMapping("listar")
    @ApiOperation("Endpoint para listar personas, con filtros y paginados")
    public ResponseEntity<Map<String, Object>> listarItems(@RequestParam(defaultValue = "0")  Integer paginaActual,
                                                            @RequestParam(defaultValue = "5") Integer totalRegistros,
                                                            @RequestParam(required = false) TipoDocumentoEnum tipoDocumentoEnum,
                                                            @RequestParam(required = false, defaultValue = "")   String nombre){
        log.info(":::::::: ENDPOINT LISTAR PERSONAS ::::::::");
        Map<String,Object> respuesta = personaService.listarPersonas(paginaActual,totalRegistros,tipoDocumentoEnum,nombre);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);

    }

    /**
     * Método para buscar persona por su id.
     * @param id de la persona a buscar.
     * @return persona con el id ingresado.
     */
    @GetMapping("{id}")
    @ApiOperation("Endpoint para buscar persona por ID")
    public ResponseEntity<PersonaDTO> buscarPersonaPorId(@PathVariable  Long id){
        log.info(":::::::: ENDPOINT BUSCAR PERSONA POR ID ::::::::");
        PersonaDTO persona = personaService.buscarPersonaPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(persona);
    }

    /**
     * Método para eliminar persona.
     * @param idPersona para eliminar registro.
     * @return true o false segun corresponda.
     */
    @DeleteMapping("{idPersona}")
    @ApiOperation("Endpoint para eliminar persona")
    public ResponseEntity<Boolean> eliminarPersona(@PathVariable  Long idPersona){
        log.info(":::::::: ENDPOINT BORRAR PERSONA ::::::::");
        Boolean respuesta = personaService.borrarPersona(idPersona);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

}
