package soap.service;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import soap.controle.ClienteControle;
import soap.model.Cliente;

/**
 * Web Service SOAP para o CRUD de Cliente.
 * Cada método público vira uma operação SOAP (visível no WSDL).
 */
@WebService(serviceName = "ClienteService")
public class ClienteService {

    private final ClienteControle controle = new ClienteControle();

    @WebMethod(operationName = "inserirCliente")
    public Cliente inserirCliente(String nome, String telefone, String endereco) {
        return controle.inserir(nome, telefone, endereco);
    }

    @WebMethod(operationName = "alterarCliente")
    public Cliente alterarCliente(int id, String nome, String telefone, String endereco) {
        return controle.alterar(id, nome, telefone, endereco);
    }

    @WebMethod(operationName = "excluirCliente")
    public boolean excluirCliente(int id) {
        return controle.excluir(id);
    }

    @WebMethod(operationName = "consultarCliente")
    public Cliente consultarCliente(int id) {
        return controle.consultar(id);
    }

    @WebMethod(operationName = "listarClientes")
    public List<Cliente> listarClientes() {
        return controle.listarTodos();
    }
}
