package com.pizzaria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados para alterar um pedido existente")
public record PedidoAlteracaoRequest(
        @NotBlank(message = "Pedido precisa ter ao menos um item.")
        @Schema(description = "Novos itens do pedido", example = "3x Pizza Calabresa")
        String itens,

        @NotNull(message = "O valor total é obrigatório.")
        @Positive(message = "Valor total do pedido deve ser maior que zero.")
        @Schema(description = "Novo valor total em reais", example = "120.00")
        Double valorTotal) {
}
