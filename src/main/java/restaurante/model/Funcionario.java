package restaurante.model;

import restaurante.enums.CargoFuncionario;

public class Funcionario {
    /*@ public invariant id > 0; @*/
    /*@ public invariant nome != null && !nome.equals(""); @*/
    /*@ public invariant cargo != null; @*/

    private final int id;
    private final String nome;
    private final CargoFuncionario cargo;

    /*@
      @ requires id > 0;
      @ requires nome != null && !nome.isEmpty();
      @ requires cargo != null;
      @ ensures this.id == id;
      @ ensures this.nome == nome;
      @ ensures this.cargo == cargo;
      @*/
    public Funcionario(int id, String nome, CargoFuncionario cargo) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
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
    public CargoFuncionario getCargo() {
        return cargo;
    }

    /*@ skipesc @*/
    @Override
    public String toString() {
        return "Funcionario #" + id + "\nNome: " + nome + "\nCargo: " + cargo;
    }
}
