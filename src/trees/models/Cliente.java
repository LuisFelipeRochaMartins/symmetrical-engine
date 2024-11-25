package trees.models;

public class Cliente {

    public Integer id;
    public String cpf;
    public String nome;

    public Cliente() {}

    public Cliente(Integer id, String cpf, String nome) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return String.format("Cliente(id=%d, cpf=%s, nome=%s)", id, cpf, nome);
    }
}
