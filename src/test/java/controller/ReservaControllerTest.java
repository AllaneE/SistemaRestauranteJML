package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurante.controller.ClienteController;
import restaurante.controller.MesaController;
import restaurante.controller.ReservaController;
import restaurante.exception.MesaIndisponivelException;
import restaurante.exception.ReservaIndisponivelException;
import restaurante.exception.ReservaNaoEncontradaException;
import restaurante.exception.ValorInvalidoException;
import restaurante.model.Cliente;
import restaurante.model.Mesa;
import restaurante.model.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaControllerTest {

    private ReservaController reservaController;
    private ClienteController clienteController;
    private MesaController mesaController;
    private Cliente cliente;
    private Mesa mesa;
    private LocalDate data;
    private LocalTime horario;

    @BeforeEach
    void setUp() {
        reservaController = new ReservaController();
        clienteController = new ClienteController();
        mesaController = new MesaController();
        cliente = clienteController.cadastrarCliente("Joao Silva", "83999999999");
        mesa = mesaController.CadastroMesa(1, 4);
        data = LocalDate.now().plusDays(1);
        horario = LocalTime.of(19, 0);
    }

    @Test
    void deveSolicitarReservaValida() {
        Reserva reserva = reservaController.solicitarReserva(cliente, mesa, data, horario, 4);
        assertNotNull(reserva);
        assertEquals(1, reservaController.listarReservas().size());
    }

    @Test
    void naoDevePermitirQuantidadeAcimaDaCapacidadeDaMesa() {
        assertThrows(ValorInvalidoException.class,
                () -> reservaController.solicitarReserva(cliente, mesa, data, horario, 5));
    }

    @Test
    void naoDevePermitirDuasReservasAtivasMesmaMesaDataEHorario() {
        reservaController.solicitarReserva(cliente, mesa, data, horario, 2);
        Cliente outroCliente = clienteController.cadastrarCliente("Maria", "83988888888");
        assertThrows(ReservaIndisponivelException.class,
                () -> reservaController.solicitarReserva(outroCliente, mesa, data, horario, 2));
    }

    @Test
    void devePermitirNovaReservaAposCancelamentoDaAnterior() {
        Reserva primeira = reservaController.solicitarReserva(cliente, mesa, data, horario, 2);
        primeira.cancelar();
        Cliente outroCliente = clienteController.cadastrarCliente("Maria", "83988888888");
        assertDoesNotThrow(() -> reservaController.solicitarReserva(outroCliente, mesa, data, horario, 2));
    }

    @Test
    void naoDeveReservarMesaEmManutencao() {
        mesa.emManutencao();
        assertThrows(MesaIndisponivelException.class,
                () -> reservaController.solicitarReserva(cliente, mesa, data, horario, 2));
    }

    @Test
    void cadaReservaDeveTerIdUnico() {
        Reserva r1 = reservaController.solicitarReserva(cliente, mesa, data, horario, 2);
        Mesa outraMesa = mesaController.CadastroMesa(2, 4);
        Reserva r2 = reservaController.solicitarReserva(cliente, outraMesa, data, horario, 2);
        assertNotEquals(r1.getId(), r2.getId());
    }

    @Test
    void deveLancarExcecaoAoConsultarReservaInexistente() {
        assertThrows(ReservaNaoEncontradaException.class, () -> reservaController.consultarReserva(999));
    }

    @Test
    void deveRealizarFluxoCompletoDeCheckIn() {
        Reserva reserva = reservaController.solicitarReserva(cliente, mesa, data, horario, 2);
        reservaController.confirmarReserva(reserva.getId());
        reservaController.realizarReserva(reserva.getId());
        assertEquals(restaurante.enums.StatusMesa.OCUPADA, mesa.getStatus());
    }
}
