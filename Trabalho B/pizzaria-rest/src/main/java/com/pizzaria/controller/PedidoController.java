package com.pizzaria.controller;

import com.pizzaria.dto.CancelamentoRequest;
import com.pizzaria.dto.ErroResposta;
import com.pizzaria.dto.PedidoAlteracaoRequest;
import com.pizzaria.dto.PedidoRequest;
import com.pizzaria.exception.RecursoNaoEncontradoException;
import com.pizzaria.exception.RegraDeNegocioException;
import com.pizzaria.model.Cliente;
import com.pizzaria.model.Pedido;
import com.pizzaria.model.StatusPedido;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Criação, consulta, alteração, cancelamento e exclusão de pedidos")
public class PedidoController {

    private final PedidoRepository pedidos;
    private final ClienteRepository clientes;

    public PedidoController(PedidoRepository pedidos, ClienteRepository clientes) {
        this.pedidos = pedidos;
        this.clientes = clientes;
    }

    @GetMapping
    @Operation(summary = "Listar pedidos", description = "Retorna todos os pedidos (criados e cancelados).")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos (pode ser vazia)",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Pedido.class))))
    public List<Pedido> listar() {
        return pedidos.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por id", description = "Retorna um único pedido, com o cliente e o status.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
    })
    public Pedido buscar(@PathVariable Long id) {
        return pedidos.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido " + id + " não encontrado."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar pedido",
            description = "Cria um pedido para um cliente existente. O status inicial é CRIADO.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (cliente ausente, itens vazios, valor menor ou igual a zero)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "404", description = "Cliente informado não existe",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
    })
    public Pedido criar(@Valid @RequestBody PedidoRequest req) {
        Cliente cliente = clientes.findById(req.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente " + req.clienteId() + " não encontrado."));

        Pedido p = new Pedido();
        p.setCliente(cliente);
        p.setItens(req.itens().trim());
        p.setValorTotal(req.valorTotal());
        p.setStatus(StatusPedido.CRIADO);
        return pedidos.save(p);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar pedido", description = "Altera os itens e o valor total de um pedido que ainda não foi cancelado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido alterado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "409", description = "Pedido cancelado não pode ser alterado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
    })
    public Pedido atualizar(@PathVariable Long id, @Valid @RequestBody PedidoAlteracaoRequest req) {
        Pedido p = buscar(id);
        if (p.getStatus() == StatusPedido.CANCELADO) {
            throw new RegraDeNegocioException("Pedido " + id + " está cancelado e não pode ser alterado.");
        }
        p.setItens(req.itens().trim());
        p.setValorTotal(req.valorTotal());
        return pedidos.save(p);
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido",
            description = "Cancela um pedido (ex.: pizza veio errada) registrando o motivo. Muda o status para CANCELADO.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido cancelado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "400", description = "Motivo ausente ou em branco",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "409", description = "Pedido já estava cancelado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
    })
    public Pedido cancelar(@PathVariable Long id, @Valid @RequestBody CancelamentoRequest req) {
        Pedido p = buscar(id);
        if (p.getStatus() == StatusPedido.CANCELADO) {
            throw new RegraDeNegocioException("Pedido " + id + " já está cancelado.");
        }
        p.setStatus(StatusPedido.CANCELADO);
        p.setMotivoCancelamento(req.motivo());
        return pedidos.save(p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir pedido", description = "Remove definitivamente um pedido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido excluído (sem corpo na resposta)"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResposta.class)))
    })
    public void excluir(@PathVariable Long id) {
        pedidos.delete(buscar(id));
    }
}
