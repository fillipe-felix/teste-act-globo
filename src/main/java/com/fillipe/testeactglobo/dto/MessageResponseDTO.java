package com.fillipe.testeactglobo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponseDTO {

    private String id;
    private String message;
}
