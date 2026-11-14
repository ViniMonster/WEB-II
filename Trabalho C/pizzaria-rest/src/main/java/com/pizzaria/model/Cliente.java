package com.pizzaria.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Mesmas regras de validação da classe Cliente do trabalho SOAP. */
@Entity
@Schema(description = "Cliente da pizzaria. Um cliente pode ter vários pedidos.")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador gerado automaticamente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Nome do cliente não pode ser vazio.")
    @Size(min = 3, message = "Nome do cliente deve ter ao menos 3 caracteres.")
    @Schema(description = "Nome do cliente (mínimo 3 caracteres)", example = "Joao Silva")
    private String nome;

    @NotBlank(message = "Telefone do cliente não pode ser vazio.")
    @Pattern(regexp = "[0-9()\\-\\s]{8,20}", message = "Telefone inválido. Use apenas números, parênteses, hífen e espaço (8 a 20 caracteres).")
    @Schema(description = "Telefone (números, parênteses, hífen e espaço)", example = "(47) 99999-9999")
    private String telefone;

    @NotBlank(message = "Endereço do cliente não pode ser vazio.")
    @Schema(description = "Endereço de entrega", example = "Rua A, 123 - Blumenau")
    private String endereco;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}
