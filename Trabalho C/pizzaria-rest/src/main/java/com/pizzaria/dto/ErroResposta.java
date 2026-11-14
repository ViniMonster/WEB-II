package com.pizzaria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Formato padrão das respostas de erro")
public record ErroResposta(
        @Schema(description = "Código HTTP do erro", example = "404")
        int status,
        @Schema(description = "Mensagem explicando o erro", example = "Cliente 99 não encontrado.")
        String mensagem,
        @Schema(description = "Momento em que o erro ocorreu")
        LocalDateTime timestamp) {
}
