package service;
import com.trimix.backendpersonas.dtos.PersonaDTO;
import com.trimix.backendpersonas.entity.PersonaEntity;
import com.trimix.backendpersonas.entity.TipoDocumentoEnum;
import com.trimix.backendpersonas.repository.PersonaRepository;
import com.trimix.backendpersonas.service.impl.PersonaServiceImpl;
import com.trimix.backendpersonas.specification.PersonaSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PersonaServiceTest {

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PersonaSpecification personaSpecification;

    @InjectMocks
    private PersonaServiceImpl personaService;

    private static final String TEXTO_GENERICO = "PRUEBA_TEST";

    private PersonaEntity personaEntity;
    private PersonaDTO personaDTO;

    @BeforeEach
    void setUp() {
        personaEntity = PersonaEntity.builder()
                .id(1L)
                .nombre(TEXTO_GENERICO)
                .apellido(TEXTO_GENERICO)
                .tipoDocumento(TipoDocumentoEnum.DNI)
                .numeroDocumento(TEXTO_GENERICO)
                .build();

        personaDTO = PersonaDTO.builder()
                .id(1L)
                .nombre(TEXTO_GENERICO)
                .apellido(TEXTO_GENERICO)
                .tipoDocumento(TipoDocumentoEnum.DNI)
                .numeroDocumento(12345678L)
                .build();
    }

    @Test
    void guardarPersonaTest() {
        when(modelMapper.map(any(PersonaDTO.class), eq(PersonaEntity.class))).thenReturn(personaEntity);
        when(personaRepository.save(any(PersonaEntity.class))).thenReturn(personaEntity);
        when(modelMapper.map(any(PersonaEntity.class), eq(PersonaDTO.class))).thenReturn(personaDTO);
        PersonaDTO result = personaService.guardarPersona(personaDTO);
        assertNotNull(result);
        assertEquals(personaDTO, result);
    }

    @Test
    void editarPersonaTest() {
        when(personaRepository.save(any(PersonaEntity.class))).thenReturn(personaEntity);
        when(modelMapper.map(any(PersonaDTO.class), eq(PersonaEntity.class))).thenReturn(personaEntity);
        when(modelMapper.map(any(PersonaEntity.class), eq(PersonaDTO.class))).thenReturn(personaDTO);
        PersonaDTO result = personaService.editarPersona(personaDTO);
        assertNotNull(result);
        assertEquals(personaDTO, result);
    }

    @Test
    void borrarPersonaTest() {
        when(personaRepository.findById(anyLong())).thenReturn(Optional.of(personaEntity));
        Boolean result = personaService.borrarPersona(1L);
        assertTrue(result);
    }


    @Test
    void buscarPersonaPorIdTest() {
        when(personaRepository.findById(anyLong())).thenReturn(Optional.of(personaEntity));
        when(modelMapper.map(any(PersonaEntity.class), eq(PersonaDTO.class))).thenReturn(personaDTO);
        PersonaDTO result = personaService.buscarPersonaPorId(1L);
        assertNotNull(result);
        assertEquals(personaDTO, result);
    }
}
