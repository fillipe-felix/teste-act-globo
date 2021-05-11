package com.fillipe.testeactglobo.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario implements Serializable {

    @Id
    private String documento;
    private String nome;
    private int idade;

    private String CEP;
    @JsonAlias("localidade")
    private String cidade;
    private String bairro;
    @JsonAlias("uf")
    private String estado;

}
