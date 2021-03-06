package com.fillipe.testeactglobo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fillipe.testeactglobo.dto.MessageResponseDTO;
import com.fillipe.testeactglobo.dto.UsuarioDTO;
import com.fillipe.testeactglobo.entity.Usuario;
import com.fillipe.testeactglobo.exception.UsuarioLocalDateException;
import com.fillipe.testeactglobo.exception.UsuarioNotFoundException;
import com.fillipe.testeactglobo.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public MessageResponseDTO cadastrarUsuario(UsuarioDTO usuario) throws UsuarioLocalDateException {

        String URL = "https://viacep.com.br/ws/" + usuario.getCep() + "/json";
        Usuario objUsuario = new Usuario();
        MessageResponseDTO messageResponseDTO = null;

        try {
            if(usuarioRepository.findByDocumento(usuario.getDocumento()).isEmpty()){
                HttpResponse<String> response = criaRequisiçãoGET(URL);

                if(response.statusCode() == 200 && !response.body().contains("erro")){
                    objUsuario = desseralizarObjeto(response.body());
                    toObjUsuario(usuario, objUsuario);
                    objUsuario = usuarioRepository.save(objUsuario);
                    messageResponseDTO = criarMessageResponse("Usuário cadastrado com sucesso", objUsuario.getDocumento());
                } else {
                    messageResponseDTO = criarMessageResponse("CEP inválido, por favor verifique o numero digitado", "");
                }
            } else {
                messageResponseDTO = criarMessageResponse("Usuário ja cadastrado, por favor verifique o documento", "");
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return messageResponseDTO;
    }

    private void toObjUsuario(UsuarioDTO dtoUsuario, Usuario usuario) throws UsuarioLocalDateException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate dataNascimento = LocalDate.parse(dtoUsuario.getDataNascimento().replace("-", "/"), formatter);
        LocalDate hoje = LocalDate.now();

        if(dataNascimento.isBefore(hoje)){
            usuario.setNome(dtoUsuario.getNome());
            usuario.setIdade(Period.between(dataNascimento, hoje).getYears());
            usuario.setDocumento(dtoUsuario.getDocumento());
        } else {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            throw new UsuarioLocalDateException(hoje.format(format));
        }

    }

    public Usuario desseralizarObjeto(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(json, new TypeReference<Usuario>(){});
    }

    public HttpResponse<String> criaRequisiçãoGET(String url) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = null;
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public Usuario buscarUsuarioPorDocumento(String documento) throws UsuarioNotFoundException {
        return usuarioRepository.findByDocumento(documento).orElseThrow(() -> new UsuarioNotFoundException(documento));
    }

    public MessageResponseDTO criarMessageResponse(String message, String id) {
        return MessageResponseDTO
                .builder()
                .message(message)
                .id(id)
                .build();
    }
}
