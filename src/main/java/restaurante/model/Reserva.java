package restaurante.model;

import restaurante.enums.StatusMesa;
import restaurante.enums.StatusReserva;
import restaurante.exception.ValorInvalidoException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    /*@ public invariant id > 0; @*/
    /*@ public invariant cliente != null; @*/
    /*@ public invariant mesa != null; @*/
    /*@ public invariant data != null; @*/
    /*@ public invariant horario != null; @*/
    /*@ public invariant quantidadePessoas > 0; @*/
    /*@ public invariant quantidadePessoas <= mesa.getCapacidade(); @*/
    /*@ public invariant status != null; @*/

    private final int id;
    private final Cliente cliente;
    private final Mesa mesa;
    private final LocalDate data;
    private final LocalTime horario;
    private final int quantidadePessoas;
    private StatusReserva status;

    /*@
      @ requires id > 0;
      @ requires cliente != null;
      @ requires mesa != null;
      @ requires data != null;
      @ requires horario != null;
      @ requires quantidadePessoas > 0 && quantidadePessoas <= mesa.getCapacidade();
      @ ensures this.id == id;
      @ ensures this.cliente == cliente;
      @ ensures this.mesa == mesa;
      @ ensures this.data == data;
      @ ensures this.horario == horario;
      @ ensures this.quantidadePessoas == quantidadePessoas;
      @ ensures this.status == StatusReserva.AGENDADA;
      @*/
    public Reserva(int id, Cliente cliente, Mesa mesa, LocalDate data, LocalTime horario, int quantidadePessoas) {
        this.id = id;
        this.cliente = cliente;
        this.mesa = mesa;
        this.data = data;
        this.horario = horario;
        this.quantidadePessoas = quantidadePessoas;
        this.status = StatusReserva.AGENDADA;
    }

    /*@ pure @*/
    public int getId() {
        return id;
    }

    /*@ pure @*/
    public Cliente getCliente() {
        return cliente;
    }

    /*@ pure @*/
    public Mesa getMesa() {
        return mesa;
    }

    /*@ pure @*/
    public LocalDate getData() {
        return data;
    }

    /*@ pure @*/
    public LocalTime getHorario() {
        return horario;
    }

    /*@ pure @*/
    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    /*@ pure @*/
    public StatusReserva getStatus() {
        return status;
    }

    /*@
      @ requires this.status == StatusReserva.AGENDADA;
      @ assignable this.status;
      @ ensures this.status == StatusReserva.CONFIRMADA;
      @ signals (ValorInvalidoException e) (\old(this.status) != StatusReserva.AGENDADA);
      @*/
    public void confirmar(){
        if(status != StatusReserva.AGENDADA){
            throw new ValorInvalidoException("Não é possível confirmar uma reserva que não está agendada.");
        }
        status = StatusReserva.CONFIRMADA;
    }

    /*@
      @ requires this.status == StatusReserva.AGENDADA;
      @ assignable this.status;
      @ ensures this.status == StatusReserva.CANCELADA;
      @ signals (ValorInvalidoException e) (\old(this.status) != StatusReserva.AGENDADA);
      @*/
    public void cancelar(){
        if(status != StatusReserva.AGENDADA){
            throw new ValorInvalidoException("Não é possível cancelar uma reserva que não está agendada.");
        }
        status = StatusReserva.CANCELADA;
    }

    /*@
      @ requires this.status == StatusReserva.AGENDADA || this.status == StatusReserva.CONFIRMADA;
      @ requires mesa.getStatus() == StatusMesa.LIVRE;
      @ assignable \everything;
      @ ensures mesa.getStatus() == StatusMesa.OCUPADA;
      @ ensures this.status == \old(this.status);
      @ signals (ValorInvalidoException e)
      @         (\old(this.status) != StatusReserva.AGENDADA && \old(this.status) != StatusReserva.CONFIRMADA);
      @*/
    public void realizarReserva(){
        if(status != StatusReserva.AGENDADA && status != StatusReserva.CONFIRMADA){
            throw new ValorInvalidoException("Não é possível realizar uma reserva que não está agendada ou confirmada.");
        }
        mesa.ocuparMesa();
    }

    /*@
      @ requires this.status == StatusReserva.AGENDADA || this.status == StatusReserva.CONFIRMADA;
      @ assignable this.status;
      @ ensures this.status == StatusReserva.NAO_APARECEU;
      @ signals (ValorInvalidoException e)
      @         (\old(this.status) != StatusReserva.AGENDADA && \old(this.status) != StatusReserva.CONFIRMADA);
      @*/
    public void marcarNaoCompareceu(){
        if(status != StatusReserva.AGENDADA && status != StatusReserva.CONFIRMADA){
            throw new ValorInvalidoException("Não é possível marcar como não compareceu uma reserva que não está agendada ou confirmada.");
        }
        status = StatusReserva.NAO_APARECEU;
    }

    /*@
      @ assignable this.status;
      @ ensures this.status == StatusReserva.FINALIZADA;
      @*/
    public void finalizar(){
        status = StatusReserva.FINALIZADA;
    }

    /*@
      @ requires outraReserva != null ==> outraReserva.getData() != null && outraReserva.getHorario() != null;
      @ ensures outraReserva == null ==> \result == false;
      @ ensures outraReserva != null ==> \result == (
      @     mesa.getNumero() == outraReserva.getMesa().getNumero()
      @     && data.equals(outraReserva.getData())
      @     && horario.equals(outraReserva.getHorario())
      @     && (status != StatusReserva.CANCELADA && status != StatusReserva.NAO_APARECEU)
      @     && (outraReserva.getStatus() != StatusReserva.CANCELADA && outraReserva.getStatus() != StatusReserva.NAO_APARECEU)
      @ );
      @ pure
      @*/
    public boolean conflito(Reserva outraReserva){
        if(outraReserva ==null){
            return false;
        }
        boolean estaAtiva = status != StatusReserva.CANCELADA && status != StatusReserva.NAO_APARECEU;
        boolean outraAtiva = outraReserva.status != StatusReserva.CANCELADA && outraReserva.status != StatusReserva.NAO_APARECEU;
        return mesa.getNumero() == outraReserva.mesa.getNumero() && data.equals(outraReserva.data) && horario.equals(outraReserva.horario) && estaAtiva && outraAtiva;
    }

    @Override
    public String toString(){
        return "Reserva ID: " + id + "\nCliente: " + cliente.getNome() + "\nMesa: " + mesa.getNumero() + "\nData: " + data + "\nHorário: " + horario + "\nQuantidade de Pessoas: " + quantidadePessoas + "\nStatus: " + status;
    }
}
