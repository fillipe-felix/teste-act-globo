package com.fillipe.testeactglobo.controller;

import com.fillipe.testeactglobo.dto.MessageResponseDTO;
import com.fillipe.testeactglobo.dto.UsuarioDTO;
import com.fillipe.testeactglobo.entity.Usuario;
import com.fillipe.testeactglobo.exception.UsuarioNotFoundException;
import com.fillipe.testeactglobo.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.fillipe.testeactglobo.utils.UsuarioUtils.asJsonString;
import static com.fillipe.testeactglobo.utils.UsuarioUtils.criaFakeDTO;
import static com.fillipe.testeactglobo.utils.UsuarioUtils.criaFakeRetorno;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    private static final String USUARIO_API_URL_PATH = "/api/v1/usuario";

    private MockMvc mockMvc;

    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioController = new UsuarioController(usuarioService);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void testCadastroUsuario() throws Exception {
        UsuarioDTO usuarioDTOEsperado = criaFakeDTO();
        MessageResponseDTO mensagemSucessoEsperada = mensagemSucessoEsperada("487611974");

        when(usuarioService.cadastrarUsuario(usuarioDTOEsperado)).thenReturn(mensagemSucessoEsperada);

        mockMvc.perform(post(USUARIO_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(usuarioDTOEsperado)))
                .andExpect(status().isCreated())
                //.andDo(print())
                .andExpect(jsonPath("$.message", is(mensagemSucessoEsperada.getMessage())));
    }

    @Test
    void testbuscarUsuarioMensagemSucesso() throws Exception {
        String documentoEsperado = "487611974";
        Usuario retornoEsperado = criaFakeRetorno();

        when(usuarioService.buscarUsuarioPorDocumento(documentoEsperado)).thenReturn(retornoEsperado);

        mockMvc.perform(get(USUARIO_API_URL_PATH + "/" + documentoEsperado)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documento", is(documentoEsperado)))
                .andExpect(jsonPath("$.nome", is("Fillipe")));
    }

    @Test
    void testbuscarUsuarioMensagemErro() throws Exception {
        String documentoEsperado = "487611974";
        Usuario retornoEsperado = criaFakeRetorno();

        when(usuarioService.buscarUsuarioPorDocumento(documentoEsperado)).thenThrow(UsuarioNotFoundException.class);

        mockMvc.perform(get(USUARIO_API_URL_PATH + "/" + documentoEsperado)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private MessageResponseDTO mensagemSucessoEsperada(String documento) {
        return MessageResponseDTO
                .builder()
                .message("Usuário cadastrado com sucesso")
                .id(documento)
                .build();
    }
}
