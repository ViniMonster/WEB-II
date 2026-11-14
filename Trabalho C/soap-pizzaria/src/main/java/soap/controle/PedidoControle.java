package soap.controle;

import java.util.ArrayList;
import java.util.List;
import soap.model.Pedido;

/**
 * Classe de controle do Pedido.
 * Usa uma List para simular a persistência em banco de dados.
 */
public class PedidoControle {

    private static final List<Pedido> pedidos = new ArrayList<>();
    private static int proximoId = 1;

    public Pedido inserir(int clienteId, String itens, double valorTotal) {
        Pedido pedido = new Pedido(proximoId, clienteId, itens, valorTotal);
        pedidos.add(pedido);
        proximoId++;
        return pedido;
    }

    public Pedido alterar(int id, String itens, double valorTotal) {
        Pedido pedido = consultar(id);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido com id " + id + " não encontrado.");
        }
        pedido.setItens(itens);
        pedido.setValorTotal(valorTotal);
        return pedido;
    }

    public boolean excluir(int id) {
        Pedido pedido = consultar(id);
        if (pedido == null) {
            return false;
        }
        return pedidos.remove(pedido);
    }

    public Pedido consultar(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos);
    }

    /** Demonstra a relação 1:N - todos os pedidos de um cliente específico. */
    public List<Pedido> listarPorCliente(int clienteId) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getClienteId() == clienteId) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}
