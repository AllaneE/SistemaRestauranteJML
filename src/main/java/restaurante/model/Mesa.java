package restaurante.model;

import restaurante.enums.StatusMesa;

public class Mesa {
    private final int numero;
    private final int capacidade;
    private StatusMesa status;

    public Mesa(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.status = StatusMesa.LIVRE; // Inicialmente, a mesa está livre
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public StatusMesa getStatus() {
        return status;
    }

    public void ocuparMesa() {
        if (status != StatusMesa.LIVRE) {
            throw new restaurante.exception.ValorInvalidoException("A mesa não está livre para ser ocupada.");
        }
        status = StatusMesa.OCUPADA;
    }

    public void desocuparMesa() {
        if(status != StatusMesa.OCUPADA) {
            throw new restaurante.exception.ValorInvalidoException("A mesa não está ocupada.");
        }
        status = StatusMesa.LIVRE;
    }

    public void emManutencao() {
        if (status != StatusMesa.LIVRE) {
            throw new restaurante.exception.ValorInvalidoException("A mesa não está livre para entrar em manutenção.");
        }
        status = StatusMesa.MANUTENCAO;
    }

    public void retirarManutencao() {
        if (status != StatusMesa.MANUTENCAO) {
            throw new restaurante.exception.ValorInvalidoException("A mesa não está em manutenção.");
        }
        status = StatusMesa.LIVRE;
    }

    @Override
    public String toString() {
        return "Mesa" + numero + "\nCapacidade: " + capacidade + "\nStatus: " + status;
    }
}
