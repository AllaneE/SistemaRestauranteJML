package restaurante.model;

public class Cliente {
    /*@ public invariant id > 0; @*/
    /*@ public invariant nome != null && !nome.equals(""); @*/
    /*@ public invariant telefone != null && !telefone.equals(""); @*/

    private final int id;
    private String nome;
    private String telefone;

    /*@
      @ requires id > 0;
      @ requires nome != null && !nome.isEmpty();
      @ requires telefone != null && !telefone.isEmpty();
      @ ensures this.id == id;
      @ ensures this.nome == nome;
      @ ensures this.telefone == telefone;
      @*/
    public Cliente(int id, String nome, String telefone) {
        if (id <= 0) {
            throw new restaurante.exception.ValorInvalidoException("O ID do cliente deve ser maior que zero.");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new restaurante.exception.ValorInvalidoException("Nome do cliente não pode ser vazio.");
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new restaurante.exception.ValorInvalidoException("Telefone do cliente não pode ser vazio.");
        }
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    /*@ pure @*/
    public int getId() {
        return id;
    }

    /*@ pure @*/
    public String getNome() {
        return nome;
    }

    /*@ pure @*/
    public String getTelefone() {
        return telefone;
    }

    /*@
      @ requires novoNome != null && !novoNome.isEmpty();
      @ requires novoTelefone != null && !novoTelefone.isEmpty();
      @ assignable this.nome, this.telefone;
      @ ensures this.nome == novoNome;
      @ ensures this.telefone == novoTelefone;
      @*/
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

    /*@ skipesc @*/
    @Override
    public String toString() {
        return "Cliente #" + id + "\nNome: " + nome + "\nTelefone: " + telefone;
    }

}
