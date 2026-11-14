package com.pizzaria.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/** Mesma estrutura do Pedido do trabalho SOAP (cliente, itens, valorTotal), com status para cancelamento. */
@Entity
@Schema(description = "Pedido feito por um cliente")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador gerado automaticamente", example = "1")
    private Long id;

    @ManyToOne(optional = false)
    @Schema(description = "Cliente dono do pedido")
    private Cliente cliente;

    @Schema(description = "Itens do pedido", example = "2x Pizza Calabresa, 1x Refrigerante")
    private String itens;

    @Schema(description = "Valor total em reais (maior que zero)", example = "89.90")
    private Double valorTotal;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Situação do pedido", example = "CRIADO")
    private StatusPedido status;

    @Schema(description = "Motivo informado no cancelamento (nulo se não cancelado)", example = "Pizza veio errada")
    private String motivoCancelamento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getItens() { return itens; }
    public void setItens(String itens) { this.itens = itens; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    public String getMotivoCancelamento() { return motivoCancelamento; }
    public void setMotivoCancelamento(String motivoCancelamento) { this.motivoCancelamento = motivoCancelamento; }
}
