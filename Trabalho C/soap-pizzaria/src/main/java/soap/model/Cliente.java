package soap.model;

/**
 * Classe modelo Cliente.
 * Um Cliente pode ter vários Pedidos (relação 1:N com Pedido).
 */
public class Cliente {

    private int id;
    private String nome;
    private String telefone;
    private String endereco;

    public Cliente() {
    }

    public Cliente(int id, String nome, String telefone, String endereco) {
        setId(id);
        setNome(nome);
        setTelefone(telefone);
        setEndereco(endereco);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id do cliente não pode ser negativo.");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        }
        if (nome.trim().length() < 3) {
            throw new IllegalArgumentException("Nome do cliente deve ter ao menos 3 caracteres.");
        }
        this.nome = nome.trim();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do cliente não pode ser vazio.");
        }
        if (!telefone.matches("[0-9()\\-\\s]{8,20}")) {
            throw new IllegalArgumentException("Telefone inválido. Use apenas números, parênteses, hífen e espaço.");
        }
        this.telefone = telefone.trim();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço do cliente não pode ser vazio.");
        }
        this.endereco = endereco.trim();
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nome='" + nome + "', telefone='" + telefone
                + "', endereco='" + endereco + "'}";
    }
}
