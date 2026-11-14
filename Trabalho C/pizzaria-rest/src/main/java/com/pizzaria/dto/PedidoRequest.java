package com.pizzaria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados para criar um pedido")
public record PedidoRequest(
        @NotNull(message = "O id do cliente é obrigatório.")
        @Positive(message = "Pedido precisa estar associado a um cliente válido.")
        @Schema(description = "Id de um cliente existente", example = "1")
        Long clienteId,

        @NotBlank(message = "Pedido precisa ter ao menos um item.")
        @Schema(description = "Itens do pedido", example = "2x Pizza Calabresa, 1x Refrigerante")
        String itens,

        @NotNull(message = "O valor total é obrigatório.")
        @Positive(message = "Valor total do pedido deve ser maior que zero.")
        @Schema(description = "Valor total em reais", example = "89.90")
        Double valorTotal) {
}
