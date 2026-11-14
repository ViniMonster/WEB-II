package com.pizzaria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para cancelar um pedido")
public record CancelamentoRequest(
        @NotBlank(message = "O motivo do cancelamento é obrigatório.")
        @Schema(description = "Motivo do cancelamento ou da reclamação", example = "Pizza veio errada")
        String motivo) {
}
