package com.fillipe.testeactglobo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fillipe.testeactglobo.dto.MessageResponseDTO;
import com.fillipe.testeactglobo.dto.UsuarioDTO;
import com.fillipe.testeactglobo.entity.Usuario;
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

    public MessageResponseDTO cadastrarUsuario(UsuarioDTO usuario) {

        String URL = "https://viacep.com.br/ws/" + usuario.getCep() + "/json";
        Usuario objUsuario = new Usuario();
        MessageResponseDTO messageResponseDTO = null;

        try {

            HttpResponse<String> response = criaRequisiçãoGET(URL);

            if(response.statusCode() == 200){
                objUsuario = desseralizarObjeto(response.body());
                toObjUsuario(usuario, objUsuario);
                objUsuario = usuarioRepository.save(objUsuario);
                messageResponseDTO = MessageResponseDTO
                        .builder()
                        .id(objUsuario.getId())
                        .message("Usuario cadastrado com sucesso")
                        .build();
            } else {
                messageResponseDTO = MessageResponseDTO
                        .builder()
                        .message("CEP inválido, por favor verifique o numero digitado")
                        .build();
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return messageResponseDTO;
    }

    private void toObjUsuario(UsuarioDTO dtoUsuario, Usuario usuario) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimento = LocalDate.parse(dtoUsuario.getDataNascimento(), formatter);
        LocalDate hoje = LocalDate.now();

        usuario.setNome(dtoUsuario.getNome());
        usuario.setIdade(Period.between(dataNascimento, hoje).getYears());
        usuario.setDocumento(dtoUsuario.getDocumento());

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
}
