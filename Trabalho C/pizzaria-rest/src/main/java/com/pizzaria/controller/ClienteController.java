package com.pizzaria.controller;

import com.pizzaria.dto.ErroResposta;
import com.pizzaria.exception.RecursoNaoEncontradoException;
import com.pizzaria.exception.RegraDeNegocioException;
import com.pizzaria.model.Cliente;
import com.pizzaria.model.Pedido;
import com.pizzaria.repository.ClienteRepository;
import com.pizzaria.repository.PedidoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Cadastro, consulta, alteração e exclusão de clientes")
public class ClienteController {

    private final ClienteRepository clientes;
    private final PedidoRepository pedidos;

    public ClienteController(ClienteRepository clientes, PedidoRepository pedidos) {
        this.clientes = clientes;
        this.pedidos = pedidos;
    }

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Retorna todos os clientes cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes (pode ser vazia)",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Cliente.class))))
    public List<Cliente> listar() {
        return clientes.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por id", description = "Retorna um único cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
    })
    public Cliente buscar(@PathVariable Long id) {
        return clientes.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente " + id + " não encontrado."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar cliente",
            description = "Cria um novo cliente. O id é gerado automaticamente (qualquer id enviado é ignorado).")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente criado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (nome vazio ou com menos de 3 caracteres, telefone inválido, endereço vazio, JSON malformado)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
    })
    public Cliente criar(@Valid @RequestBody Cliente cliente) {
        cliente.setId(null);
        return clientes.save(cliente);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar cliente", description = "Substitui nome, telefone e endereço de um cliente existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente alterado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
    })
    public Cliente atualizar(@PathVariable Long id, @Valid @RequestBody Cliente dados) {
        Cliente c = buscar(id);
        c.setNome(dados.getNome());
        c.setTelefone(dados.getTelefone());
        c.setEndereco(dados.getEndereco());
        return clientes.save(c);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir cliente", description = "Remove um cliente. Não é possível excluir um cliente que já tenha pedidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente excluído (sem corpo na resposta)"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "409", description = "Cliente possui pedidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
    })
    public void excluir(@PathVariable Long id) {
        Cliente c = buscar(id);
        if (!pedidos.findByClienteId(id).isEmpty()) {
            throw new RegraDeNegocioException("Cliente " + id + " possui pedidos e não pode ser excluído.");
        }
        clientes.delete(c);
    }

    @GetMapping("/{id}/pedidos")
    @Operation(summary = "Listar pedidos de um cliente",
            description = "Retorna todos os pedidos de um cliente (relação 1:N entre Cliente e Pedido).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos do cliente (pode ser vazia)",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Pedido.class)))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
    })
    public List<Pedido> listarPedidos(@PathVariable Long id) {
        buscar(id);
        return pedidos.findByClienteId(id);
    }
}
