package soap.service;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import soap.controle.PedidoControle;
import soap.model.Pedido;

/**
 * Web Service SOAP para o CRUD de Pedido.
 * Também expõe uma operação que demonstra a relação com Cliente.
 */
@WebService(serviceName = "PedidoService")
public class PedidoService {

    private final PedidoControle controle = new PedidoControle();

    @WebMethod(operationName = "inserirPedido")
    public Pedido inserirPedido(int clienteId, String itens, double valorTotal) {
        return controle.inserir(clienteId, itens, valorTotal);
    }

    @WebMethod(operationName = "alterarPedido")
    public Pedido alterarPedido(int id, String itens, double valorTotal) {
        return controle.alterar(id, itens, valorTotal);
    }

    @WebMethod(operationName = "excluirPedido")
    public boolean excluirPedido(int id) {
        return controle.excluir(id);
    }

    @WebMethod(operationName = "consultarPedido")
    public Pedido consultarPedido(int id) {
        return controle.consultar(id);
    }

    @WebMethod(operationName = "listarPedidos")
    public List<Pedido> listarPedidos() {
        return controle.listarTodos();
    }

    @WebMethod(operationName = "listarPedidosPorCliente")
    public List<Pedido> listarPedidosPorCliente(int clienteId) {
        return controle.listarPorCliente(clienteId);
    }
}
