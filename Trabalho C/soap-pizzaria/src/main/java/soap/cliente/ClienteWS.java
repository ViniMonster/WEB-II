package soap.cliente;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import soap.model.Cliente;

/**
 * Interface do lado cliente que espelha as operações do ClienteService (WSDL).
 * Substitui o stub que o wsimport geraria.
 */
@WebService(name = "ClienteService", targetNamespace = "http://service.soap/")
public interface ClienteWS {

    @WebMethod
    Cliente inserirCliente(String nome, String telefone, String endereco);

    @WebMethod
    Cliente alterarCliente(int id, String nome, String telefone, String endereco);

    @WebMethod
    boolean excluirCliente(int id);

    @WebMethod
    Cliente consultarCliente(int id);

    @WebMethod
    List<Cliente> listarClientes();
}
