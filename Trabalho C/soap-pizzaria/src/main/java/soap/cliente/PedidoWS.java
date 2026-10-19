package soap.cliente;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import soap.model.Pedido;


@WebService(name = "PedidoService", targetNamespace = "http://service.soap/")
public interface PedidoWS {

    @WebMethod
    Pedido inserirPedido(int clienteId, String itens, double valorTotal);

    @WebMethod
    Pedido alterarPedido(int id, String itens, double valorTotal);

    @WebMethod
    boolean excluirPedido(int id);

    @WebMethod
    Pedido consultarPedido(int id);

    @WebMethod
    List<Pedido> listarPedidos();

    @WebMethod
    List<Pedido> listarPedidosPorCliente(int clienteId);
}
