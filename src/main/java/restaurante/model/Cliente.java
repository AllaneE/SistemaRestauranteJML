package restaurante.model;

public class Cliente {
    private final int id;
    private String nome;
    private String telefone;

    public Cliente(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public  void atualizarDados(String novoNome, String novoTelefone){
        if(novoNome == null || novoNome.trim().isEmpty()){
            throw new restaurante.exception.ValorInvalidoException("Nome do cliente não pode ser vazio.");
        }
        if(novoTelefone == null || novoTelefone.trim().isEmpty()){
            throw new restaurante.exception.ValorInvalidoException("Telefone do cliente não pode ser vazio.");
        }
        this.nome = novoNome;
        this.telefone = novoTelefone;
    }

    @Override
    public String toString() {
        return "Cliente #" + id + "\nNome: " + nome + "\nTelefone: " + telefone;
    }

}
