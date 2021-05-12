package com.fillipe.testeactglobo.service;

import com.fillipe.testeactglobo.dto.MessageResponseDTO;
import com.fillipe.testeactglobo.dto.UsuarioDTO;
import com.fillipe.testeactglobo.entity.Usuario;
import com.fillipe.testeactglobo.exception.UsuarioLocalDateException;
import com.fillipe.testeactglobo.exception.UsuarioNotFoundException;
import com.fillipe.testeactglobo.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fillipe.testeactglobo.utils.UsuarioUtils.criaFakeDTO;
import static com.fillipe.testeactglobo.utils.UsuarioUtils.criaFakeRetorno;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testCadastrarUsuario() throws UsuarioLocalDateException {
        UsuarioDTO usuarioDTO = criaFakeDTO();
        Usuario retornoEsperado = criaFakeRetorno();

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(retornoEsperado);

        MessageResponseDTO mensagemSucessoEsperada = mensagemSucessoEsperada(retornoEsperado.getDocumento());
        MessageResponseDTO mensagemSucesso = usuarioService.cadastrarUsuario(usuarioDTO);

        assertEquals(mensagemSucesso, mensagemSucessoEsperada);
    }

    private MessageResponseDTO mensagemSucessoEsperada(String documento) {
        return MessageResponseDTO
                .builder()
                .message("Usuário cadastrado com sucesso")
                .id(documento)
                .build();
    }


    @Test
    void testBuscarUsuarioSucesso() throws UsuarioNotFoundException {
        Usuario retornoEsperado = criaFakeRetorno();

        when(usuarioRepository.findByDocumento(retornoEsperado.getDocumento())).thenReturn(Optional.of(retornoEsperado));


        Usuario usuario = usuarioService.buscarUsuarioPorDocumento(retornoEsperado.getDocumento());

        assertEquals(retornoEsperado, usuario);

        assertEquals(retornoEsperado.getDocumento(), usuario.getDocumento());
        assertEquals(retornoEsperado.getNome(), usuario.getNome());
    }

    @Test
    void testBuscarUsuarioErro() {
        String documentoInvalido = "4876119742342";
        when(usuarioRepository.findByDocumento(documentoInvalido))
                .thenReturn(Optional.ofNullable(any(Usuario.class)));

        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.buscarUsuarioPorDocumento(documentoInvalido));
    }
}
