package soap.cliente;

import java.net.URL;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPFaultException;
import soap.model.Cliente;
import soap.model.Pedido;

/**
 * Aplicação cliente que testa os Web Services SOAP.
 * Rode o Publicador antes (os serviços precisam estar no ar).
 */
public class ClienteTeste {

    private static final String NS = "http://service.soap/";

    public static void main(String[] args) throws Exception {
        Service sCliente = Service.create(
                new URL("http://localhost:8080/ws/cliente?wsdl"), new QName(NS, "ClienteService"));
        ClienteWS clientes = sCliente.getPort(ClienteWS.class);

        Service sPedido = Service.create(
                new URL("http://localhost:8080/ws/pedido?wsdl"), new QName(NS, "PedidoService"));
        PedidoWS pedidos = sPedido.getPort(PedidoWS.class);

        System.out.println("===== TESTE DO SERVICO DE CLIENTE =====");

        Cliente c = clientes.inserirCliente("Joao Silva", "(47) 99999-9999", "Rua A, 123 - Blumenau");
        System.out.println("[INSERIR]   " + c);

        System.out.println("[CONSULTAR] " + clientes.consultarCliente(c.getId()));

        Cliente alterado = clientes.alterarCliente(c.getId(), "Joao S. Silva", "(47) 98888-8888", "Rua B, 456 - Blumenau");
        System.out.println("[ALTERAR]   " + alterado);

        List<Cliente> todos = clientes.listarClientes();
        System.out.println("[LISTAR]    total de clientes = " + todos.size());

        System.out.println();
        System.out.println("===== TESTE DO SERVICO DE PEDIDO =====");

        Pedido p1 = pedidos.inserirPedido(c.getId(), "2x Pizza Calabresa, 1x Refrigerante", 89.90);
        Pedido p2 = pedidos.inserirPedido(c.getId(), "1x Pizza Portuguesa", 54.50);
        System.out.println("[INSERIR]   " + p1);
        System.out.println("[INSERIR]   " + p2);

        System.out.println("[CONSULTAR] " + pedidos.consultarPedido(p1.getId()));

        Pedido pAlterado = pedidos.alterarPedido(p1.getId(), "3x Pizza Calabresa", 120.00);
        System.out.println("[ALTERAR]   " + pAlterado);

        System.out.println("[RELACAO 1:N] pedidos do cliente " + c.getId() + ":");
        for (Pedido p : pedidos.listarPedidosPorCliente(c.getId())) {
            System.out.println("              " + p);
        }

        System.out.println();
        System.out.println("===== TESTE DAS VALIDACOES (excecoes) =====");
        try {
            clientes.inserirCliente("", "(47) 99999-9999", "Rua X");
        } catch (SOAPFaultException e) {
            System.out.println("[ERRO ESPERADO] " + e.getMessage());
        }
        try {
            pedidos.inserirPedido(c.getId(), "1x Pizza", -10);
        } catch (SOAPFaultException e) {
            System.out.println("[ERRO ESPERADO] " + e.getMessage());
        }

        System.out.println();
        System.out.println("===== EXCLUSAO =====");
        System.out.println("[EXCLUIR PEDIDO]  " + pedidos.excluirPedido(p1.getId()));
        System.out.println("[EXCLUIR CLIENTE] " + clientes.excluirCliente(c.getId()));
    }
}
