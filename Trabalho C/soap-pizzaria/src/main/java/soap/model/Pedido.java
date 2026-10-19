package soap.model;


public class Pedido {

    private int id;
    private int clienteId;
    private String itens;
    private double valorTotal;

    public Pedido() {
    }

    public Pedido(int id, int clienteId, String itens, double valorTotal) {
        setId(id);
        setClienteId(clienteId);
        setItens(itens);
        setValorTotal(valorTotal);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id do pedido não pode ser negativo.");
        }
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        if (clienteId <= 0) {
            throw new IllegalArgumentException("Pedido precisa estar associado a um cliente válido.");
        }
        this.clienteId = clienteId;
    }

    public String getItens() {
        return itens;
    }

    public void setItens(String itens) {
        if (itens == null || itens.trim().isEmpty()) {
            throw new IllegalArgumentException("Pedido precisa ter ao menos um item.");
        }
        this.itens = itens.trim();
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        if (valorTotal <= 0) {
            throw new IllegalArgumentException("Valor total do pedido deve ser maior que zero.");
        }
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Pedido{id=" + id + ", clienteId=" + clienteId + ", itens='" + itens
                + "', valorTotal=" + valorTotal + "}";
    }
}
