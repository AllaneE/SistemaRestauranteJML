package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurante.enums.StatusReserva;
import restaurante.exception.ValorInvalidoException;
import restaurante.model.Cliente;
import restaurante.model.Mesa;
import restaurante.model.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    private Cliente cliente;
    private Mesa mesa;
    private LocalDate data;
    private LocalTime horario;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1, "Joao", "83999999999");
        mesa = new Mesa(1, 4);
        data = LocalDate.now().plusDays(1);
        horario = LocalTime.of(19, 0);
    }

    @Test
    void deveCriarReservaValidaComStatusAgendada() {
        Reserva reserva = new Reserva(1, cliente, mesa, data, horario, 4);
        assertEquals(StatusReserva.AGENDADA, reserva.getStatus());
    }

    @Test
    void naoDeveAceitarQuantidadePessoasMaiorQueCapacidadeDaMesa() {
        assertThrows(ValorInvalidoException.class,
                () -> new Reserva(1, cliente, mesa, data, horario, 5));
    }

    @Test
    void devePermitirQuantidadeIgualACapacidadeDaMesa() {
        Reserva reserva = new Reserva(1, cliente, mesa, data, horario, 4);
        assertEquals(4, reserva.getQuantidadePessoas());
    }

    @Test
    void naoDeveAceitarQuantidadePessoasZeroOuNegativa() {
        assertThrows(ValorInvalidoException.class, () -> new Reserva(1, cliente, mesa, data, horario, 0));
        assertThrows(ValorInvalidoException.class, () -> new Reserva(1, cliente, mesa, data, horario, -1));
    }

    @Test
    void naoDeveAceitarClienteNulo() {
        assertThrows(ValorInvalidoException.class, () -> new Reserva(1, null, mesa, data, horario, 2));
    }

    @Test
    void naoDeveAceitarMesaNula() {
        assertThrows(ValorInvalidoException.class, () -> new Reserva(1, cliente, null, data, horario, 2));
    }

    @Test
    void naoDeveAceitarDataOuHorarioNulos() {
        assertThrows(ValorInvalidoException.class, () -> new Reserva(1, cliente, mesa, null, horario, 2));
        assertThrows(ValorInvalidoException.class, () -> new Reserva(1, cliente, mesa, data, null, 2));
    }

    @Test
    void deveConfirmarReservaAgendada() {
        Reserva reserva = new Reserva(1, cliente, mesa, data, horario, 2);
        reserva.confirmar();
        assertEquals(StatusReserva.CONFIRMADA, reserva.getStatus());
    }

    @Test
    void naoDeveConfirmarReservaJaConfirmada() {
        Reserva reserva = new Reserva(1, cliente, mesa, data, horario, 2);
        reserva.confirmar();
        assertThrows(ValorInvalidoException.class, reserva::confirmar);
    }

    @Test
    void deveCancelarReservaAgendada() {
        Reserva reserva = new Reserva(1, cliente, mesa, data, horario, 2);
        reserva.cancelar();
        assertEquals(StatusReserva.CANCELADA, reserva.getStatus());
    }

    @Test
    void naoDeveCancelarReservaJaCancelada() {
        Reserva reserva = new Reserva(1, cliente, mesa, data, horario, 2);
        reserva.cancelar();
        assertThrows(ValorInvalidoException.class, reserva::cancelar);
    }

    @Test
    void deveRealizarCheckInDeReservaAgendadaEOcuparMesa() {
        Reserva reserva = new Reserva(1, cliente, mesa, data, horario, 2);
        reserva.realizarReserva();
        assertEquals(restaurante.enums.StatusMesa.OCUPADA, mesa.getStatus());
    }

    @Test
    void naoDeveRealizarCheckInDeReservaCancelada() {
        Reserva reserva = new Reserva(1, cliente, mesa, data, horario, 2);
        reserva.cancelar();
        assertThrows(ValorInvalidoException.class, reserva::realizarReserva);
    }

    @Test
    void deveMarcarComoNaoCompareceu() {
        Reserva reserva = new Reserva(1, cliente, mesa, data, horario, 2);
        reserva.marcarNaoCompareceu();
        assertEquals(StatusReserva.NAO_APARECEU, reserva.getStatus());
    }

    @Test
    void duasReservasMesmaMesaDataEHorarioDevemConflitar() {
        Reserva r1 = new Reserva(1, cliente, mesa, data, horario, 2);
        Reserva r2 = new Reserva(2, cliente, mesa, data, horario, 2);
        assertTrue(r1.conflito(r2));
    }

    @Test
    void reservaCanceladaNaoDeveConflitarComOutra() {
        Reserva r1 = new Reserva(1, cliente, mesa, data, horario, 2);
        r1.cancelar();
        Reserva r2 = new Reserva(2, cliente, mesa, data, horario, 2);
        assertFalse(r1.conflito(r2));
    }

    @Test
    void reservasEmMesasDiferentesNaoDevemConflitar() {
        Mesa outraMesa = new Mesa(2, 4);
        Reserva r1 = new Reserva(1, cliente, mesa, data, horario, 2);
        Reserva r2 = new Reserva(2, cliente, outraMesa, data, horario, 2);
        assertFalse(r1.conflito(r2));
    }
}
