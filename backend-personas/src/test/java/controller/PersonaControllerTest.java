package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trimix.backendpersonas.BackendPersonasApplication;
import com.trimix.backendpersonas.controller.PersonasController;
import com.trimix.backendpersonas.dtos.PersonaDTO;
import com.trimix.backendpersonas.entity.TipoDocumentoEnum;
import com.trimix.backendpersonas.service.PersonaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {BackendPersonasApplication.class})
@AutoConfigureMockMvc
public class PersonaControllerTest {
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static final String TEXTO_GENERICO = "PRUEBA_TEST";
    @InjectMocks
    private PersonasController personasController;

    @Mock
    private PersonaService personaService;

    @BeforeEach
    void init(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.personasController).build();
    }

    @Test
    void crearPersonaTest()throws Exception{
        PersonaDTO personaDTO = PersonaDTO.builder()
                .nombre(TEXTO_GENERICO)
                .apellido(TEXTO_GENERICO)
                .fechaNacimiento(new Date())
                .tipoDocumento(TipoDocumentoEnum.DNI)
                .numeroDocumento(12345798L)
                .build();
        String json = objectMapper.writeValueAsString(personaDTO);
        when(personaService.guardarPersona(any(PersonaDTO.class))).thenReturn(personaDTO);
        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/personas")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PersonaDTO respuestaFinal = this.objectMapper.readValue(resultado.getResponse().getContentAsString(), new TypeReference<>(){});
        assertNotNull(respuestaFinal);
    }

    @Test
    void editarPersonaTest() throws Exception {
        PersonaDTO personaDTO = PersonaDTO.builder()
                .id(1L)
                .nombre(TEXTO_GENERICO)
                .apellido(TEXTO_GENERICO)
                .fechaNacimiento(new Date())
                .tipoDocumento(TipoDocumentoEnum.DNI)
                .numeroDocumento(12345798L)
                .build();
        String json = objectMapper.writeValueAsString(personaDTO);
        when(personaService.editarPersona(any(PersonaDTO.class))).thenReturn(personaDTO);
        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.put("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PersonaDTO respuestaFinal = objectMapper.readValue(resultado.getResponse().getContentAsString(), PersonaDTO.class);
        assertNotNull(respuestaFinal);
        assertEquals(TEXTO_GENERICO, respuestaFinal.getNombre());
        assertEquals(TEXTO_GENERICO, respuestaFinal.getApellido());
    }

    @Test
    void listarItemsTest() throws Exception {
        when(personaService.listarPersonas(anyInt(), anyInt(), any(), anyString())).thenReturn(new HashMap<>());
        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/personas/listar")
                        .param("paginaActual", "0")
                        .param("totalRegistros", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(resultado);
    }

    @Test
    void buscarPersonaPorIdTest() throws Exception {
        Long idPersona = 1L;
        PersonaDTO personaDTO = PersonaDTO.builder()
                .id(idPersona)
                .nombre(TEXTO_GENERICO)
                .apellido(TEXTO_GENERICO)
                .fechaNacimiento(new Date())
                .tipoDocumento(TipoDocumentoEnum.DNI)
                .numeroDocumento(12345798L)
                .build();
        when(personaService.buscarPersonaPorId(anyLong())).thenReturn(personaDTO);
        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/personas/{id}", idPersona)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PersonaDTO respuestaFinal = objectMapper.readValue(resultado.getResponse().getContentAsString(), PersonaDTO.class);
        assertNotNull(respuestaFinal);
        assertEquals(idPersona, respuestaFinal.getId());
    }

    @Test
    void eliminarPersonaTest() throws Exception {
        Long idPersona = 1L;
        when(personaService.borrarPersona(anyLong())).thenReturn(true);
        MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/personas/{id}", idPersona)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String respuesta = resultado.getResponse().getContentAsString();
        assertNotNull(respuesta);
        assertEquals("true", respuesta);
    }
}
