package com.fillipe.testeactglobo.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fillipe.testeactglobo.dto.UsuarioDTO;
import com.fillipe.testeactglobo.entity.Usuario;

public class UsuarioUtils {

    private static final String NOME = "Fillipe";
    private static final String DATA_NASCIMENTO = "1993/07/18";
    private static final String CEP = "18110-650";
    private static final String DOCUMENTO = "487611974";
    private static final int IDADE = 27;
    private static final String BAIRRO = "Parque Bela Vista";
    private static final String CIDADE = "Votorantim";
    private static final String ESTADO = "SP";


    public static UsuarioDTO criaFakeDTO() {
        return UsuarioDTO.builder()
                .nome(NOME)
                .dataNascimento(DATA_NASCIMENTO)
                .cep(CEP)
                .documento(DOCUMENTO)
                .build();
    }

    public static Usuario criaFakeRetorno(){
        return Usuario.builder()
                .nome(NOME)
                .idade(IDADE)
                .documento(DOCUMENTO)
                .bairro(BAIRRO)
                .cidade(CIDADE)
                .estado(ESTADO)
                .build();
    }



    public static String asJsonString(UsuarioDTO usuarioDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModules(new JavaTimeModule());

            return objectMapper.writeValueAsString(usuarioDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
