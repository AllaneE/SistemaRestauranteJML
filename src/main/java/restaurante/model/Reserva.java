package restaurante.model;

import restaurante.enums.StatusReserva;
import restaurante.exception.ValorInvalidoException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private final int id;
    private final Cliente cliente;
    private final Mesa mesa;
    private final LocalDate data;
    private final LocalTime horario;
    private final int quantidadePessoas;
    private StatusReserva status;

    public Reserva(int id, Cliente cliente, Mesa mesa, LocalDate data, LocalTime horario, int quantidadePessoas) {
        this.id = id;
        this.cliente = cliente;
        this.mesa = mesa;
        this.data = data;
        this.horario = horario;
        this.quantidadePessoas = quantidadePessoas;
        this.status = StatusReserva.AGENDADA;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void confirmar(){
        if(status != StatusReserva.AGENDADA){
            throw new ValorInvalidoException("Não é possível confirmar uma reserva que não está agendada.");
        }
        status = StatusReserva.CONFIRMADA;
    }

    public void cancelar(){
        if(status != StatusReserva.AGENDADA){
            throw new ValorInvalidoException("Não é possível cancelar uma reserva que não está agendada.");
        }
        status = StatusReserva.CANCELADA;
    }

    public void realizarReserva(){
        if(status != StatusReserva.AGENDADA && status != StatusReserva.CONFIRMADA){
            throw new ValorInvalidoException("Não é possível realizar uma reserva que não está agendada ou confirmada.");
        }
        mesa.ocuparMesa();
    }

    public void marcarNaoCompareceu(){
        if(status != StatusReserva.AGENDADA && status != StatusReserva.CONFIRMADA){
            throw new ValorInvalidoException("Não é possível marcar como não compareceu uma reserva que não está agendada ou confirmada.");
        }
        status = StatusReserva.NAO_APARECEU;
    }

    public void finalizar(){
        status = StatusReserva.FINALIZADA;
    }

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