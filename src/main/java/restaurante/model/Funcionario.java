package restaurante.model;

import restaurante.enums.CargoFuncionario;

public class Funcionario {
    private final int id;
    private final String nome;
    private final CargoFuncionario cargo;

    public Funcionario(int id, String nome, CargoFuncionario cargo) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public CargoFuncionario getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return "Funcionario #" + id + "\nNome: " + nome + "\nCargo: " + cargo;
    }
}
