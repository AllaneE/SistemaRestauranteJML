package restaurante.model;

import restaurante.enums.StatusMesa;

public class Mesa {
    /*@ public invariant numero > 0; @*/
    /*@ public invariant capacidade > 0; @*/
    /*@ public invariant status != null; @*/


    private final int numero;
    private final int capacidade;
    private StatusMesa status;

    /*@ 
      @ requires numero > 0;
      @ requires capacidade > 0;
      @ ensures this.numero == numero;
      @ ensures this.capacidade == capacidade;
      @ ensures this.status == StatusMesa.LIVRE;
      @*/
    public Mesa(int numero, int capacidade) {
        if (numero <= 0) {
            throw new restaurante.exception.ValorInvalidoException("O número da mesa deve ser maior que zero.");
        }
        if (capacidade <= 0) {
            throw new restaurante.exception.ValorInvalidoException("A capacidade da mesa deve ser maior que zero.");
        }
        this.numero = numero;
        this.capacidade = capacidade;
        this.status = StatusMesa.LIVRE; // Inicialmente, a mesa está livre
    }

    /*@ pure @*/
    public int getNumero() {
        return numero;
    }

    /*@ pure @*/
    public int getCapacidade() {
        return capacidade;
    }

    /*@ pure @*/
    public StatusMesa getStatus() {
        return status;
    }

    /*@ 
      @ requires this.status == StatusMesa.LIVRE;
      @ assignable this.status;
      @ ensures this.status == StatusMesa.OCUPADA;
      @ signals (restaurante.exception.ValorInvalidoException e) (\old(this.status) != StatusMesa.LIVRE);
      @*/
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

    /*@ 
      @ requires this.status == StatusMesa.LIVRE;
      @ assignable this.status;
      @ ensures this.status == StatusMesa.MANUTENCAO;
      @ signals (restaurante.exception.ValorInvalidoException e) (\old(this.status) != StatusMesa.LIVRE);
      @*/
    public void emManutencao() {
        if (status != StatusMesa.LIVRE) {
            throw new restaurante.exception.ValorInvalidoException("A mesa não está livre para entrar em manutenção.");
        }
        status = StatusMesa.MANUTENCAO;
    }

    /*@ 
      @ requires this.status == StatusMesa.MANUTENCAO;
      @ assignable this.status;
      @ ensures this.status == StatusMesa.LIVRE;
      @ signals (restaurante.exception.ValorInvalidoException e) (\old(this.status) != StatusMesa.MANUTENCAO);
      @*/
    public void retirarManutencao() {
        if (status != StatusMesa.MANUTENCAO) {
            throw new restaurante.exception.ValorInvalidoException("A mesa não está em manutenção.");
        }
        status = StatusMesa.LIVRE;
    }

    /*@ skipesc @*/
    @Override
    public String toString() {
        return "Mesa" + numero + "\nCapacidade: " + capacidade + "\nStatus: " + status;
    }
}
