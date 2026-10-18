package soap.controle;

import java.util.ArrayList;
import java.util.List;
import soap.model.Cliente;

/**
 * Classe de controle do Cliente.
 * Usa uma List para simular a persistência em banco de dados.
 */
public class ClienteControle {

    private static final List<Cliente> clientes = new ArrayList<>();
    private static int proximoId = 1;

    public Cliente inserir(String nome, String telefone, String endereco) {
        Cliente cliente = new Cliente(proximoId, nome, telefone, endereco);
        clientes.add(cliente);
        proximoId++;
        return cliente;
    }

    public Cliente alterar(int id, String nome, String telefone, String endereco) {
        Cliente cliente = consultar(id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente com id " + id + " não encontrado.");
        }
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        return cliente;
    }

    public boolean excluir(int id) {
        Cliente cliente = consultar(id);
        if (cliente == null) {
            return false;
        }
        return clientes.remove(cliente);
    }

    public Cliente consultar(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
}
