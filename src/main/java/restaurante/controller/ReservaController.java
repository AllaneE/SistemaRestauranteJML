package restaurante.controller;

import restaurante.enums.StatusMesa;
import restaurante.enums.StatusReserva;
import restaurante.exception.MesaIndisponivelException;
import restaurante.exception.ReservaIndisponivelException;
import restaurante.exception.ReservaNaoEncontradaException;
import restaurante.model.Cliente;
import restaurante.model.Mesa;
import restaurante.model.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReservaController {
    private final List<Reserva> reservas = new ArrayList<>();
    private int proximoId = 1;

    public Reserva solicitarReserva(Cliente cliente, Mesa mesa, LocalDate data, LocalTime hora, int quantidadePessoas) {
        if(mesa.getStatus() == StatusMesa.MANUTENCAO) {
            throw new MesaIndisponivelException("Mesa " + mesa.getNumero() + " está em manutenção.");
        }
        Reserva novaReserva = new Reserva(proximoId++, cliente, mesa, data, hora, quantidadePessoas);
        for(Reserva existente : reservas) {
            if(existente.conflito(novaReserva)) {
                throw new ReservaIndisponivelException("A mesa " + mesa.getNumero() + " já está reservada para o horário solicitado.");
            }
        }
        proximoId++;
        reservas.add(novaReserva);
        return novaReserva;
    }

    public Reserva consultarReserva(int id){
        for(Reserva reserva : reservas) {
            if(reserva.getId() == id) {
                return reserva;
            }
        }
        throw new ReservaNaoEncontradaException("Reserva com ID " + id + " não encontrada.");
    }

    public void confirmarReserva(int id){
        consultarReserva(id).confirmar();
    }

    public void cancelarReserva(int id){
        consultarReserva(id).cancelar();
    }

    public void marcarNaoCompareceu(int id){
        consultarReserva(id).marcarNaoCompareceu();
    }

    public void realizarReserva(int id){
        consultarReserva(id).realizarReserva();
    }

    public void finalizarReserva(int id){
        consultarReserva(id).finalizar();
    }

    public List<Reserva> listarReservas(){
        return Collections.unmodifiableList(reservas);
    }

    public Reserva buscarReservaEmAndamentoPorMesa(Mesa mesa){
        for(Reserva reserva : reservas) {
            boolean emAndamento = reserva.getStatus() == restaurante.enums.StatusReserva.CONFIRMADA || reserva.getStatus() == StatusReserva.AGENDADA;
            if(emAndamento && reserva.getMesa().getNumero() == mesa.getNumero()) {
                return reserva;
            }
        }
        throw new ReservaNaoEncontradaException("Não há reservas em andamento para a mesa " + mesa.getNumero() + ".");
    }

}

