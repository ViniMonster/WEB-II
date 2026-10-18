package soap.publisher;

import javax.xml.ws.Endpoint;
import soap.service.ClienteService;
import soap.service.PedidoService;

public class Publicador {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/ws/cliente", new ClienteService());
        Endpoint.publish("http://localhost:8080/ws/pedido", new PedidoService());

        System.out.println("Web Services SOAP publicados com sucesso!");
        System.out.println("WSDL Cliente: http://localhost:8080/ws/cliente?wsdl");
        System.out.println("WSDL Pedido : http://localhost:8080/ws/pedido?wsdl");
    }
}
