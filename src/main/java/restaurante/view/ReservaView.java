package restaurante.view;

import restaurante.controller.ClienteController;
import restaurante.controller.MesaController;
import restaurante.controller.PedidoController;
import restaurante.controller.ReservaController;
import restaurante.model.Cliente;
import restaurante.model.Mesa;
import restaurante.model.Pedido;
import restaurante.model.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ReservaView {
    private final Scanner scanner;
    private final ReservaController reservaController;
    private final ClienteController clienteController;
    private final MesaController mesaController;
    private final PedidoController pedidoController;

    public ReservaView(Scanner scanner, ReservaController reservaController, ClienteController clienteController,
                       MesaController mesaController, PedidoController pedidoController) {
        this.scanner = scanner;
        this.reservaController = reservaController;
        this.clienteController = clienteController;
        this.mesaController = mesaController;
        this.pedidoController = pedidoController;
    }

    public void exibirMenu() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Menu Reservas ---");
            System.out.println("1. Solicitar reserva");
            System.out.println("2. Confirmar reserva");
            System.out.println("3. Cancelar reserva");
            System.out.println("4. Realizar check-in (abre pedido automaticamente)");
            System.out.println("5. Marcar como não compareceu");
            System.out.println("6. Consultar reserva");
            System.out.println("7. Listar reservas");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            switch (lerOpcao()) {
                case 1 -> solicitar();
                case 2 -> confirmar();
                case 3 -> cancelar();
                case 4 -> checkIn();
                case 5 -> marcarNaoCompareceu();
                case 6 -> consultar();
                case 7 -> listar();
                case 0 -> continuar = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void solicitar() {
        try {
            System.out.print("Nome do cliente: ");
            String nomeCliente = scanner.nextLine().trim();
            Cliente cliente = clienteController.consultarClientePorNome(nomeCliente);

            System.out.print("Número da mesa: ");
            int numeroMesa = lerOpcao();
            Mesa mesa = mesaController.ConsultarMesa(numeroMesa);

            System.out.print("Data (yyyy-mm-dd): ");
            LocalDate data = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Horário (HH:mm): ");
            LocalTime horario = LocalTime.parse(scanner.nextLine().trim());

            System.out.print("Quantidade de pessoas: ");
            int quantidade = lerOpcao();

            Reserva reserva = reservaController.solicitarReserva(cliente, mesa, data, horario, quantidade);
            System.out.println("Reserva criada com sucesso:\n" + reserva);
        } catch (DateTimeParseException e) {
            System.out.println("Erro: data ou horário em formato inválido.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void confirmar() {
        try {
            System.out.print("ID da reserva: ");
            int id = lerOpcao();
            reservaController.confirmarReserva(id);
            System.out.println("Reserva confirmada com sucesso.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cancelar() {
        try {
            System.out.print("ID da reserva: ");
            int id = lerOpcao();
            reservaController.cancelarReserva(id);
            System.out.println("Reserva cancelada com sucesso.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void checkIn() {
        try {
            System.out.print("ID da reserva: ");
            int id = lerOpcao();
            Reserva reserva = reservaController.consultarReserva(id);
            reservaController.realizarReserva(id);
            Pedido pedido = pedidoController.abrirPedido(reserva.getCliente(), reserva.getMesa());
            System.out.println("Check-in realizado com sucesso. Mesa ocupada.");
            System.out.println("Pedido #" + pedido.getId() + " aberto automaticamente para a mesa " + reserva.getMesa().getNumero() + ".");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void marcarNaoCompareceu() {
        try {
            System.out.print("ID da reserva: ");
            int id = lerOpcao();
            reservaController.marcarNaoCompareceu(id);
            System.out.println("Reserva marcada como NÃO_COMPARECEU.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void consultar() {
        try {
            System.out.print("ID da reserva: ");
            int id = lerOpcao();
            System.out.println(reservaController.consultarReserva(id));
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        if (reservaController.listarReservas().isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }
        for (Reserva reserva : reservaController.listarReservas()) {
            System.out.println(reserva);
            System.out.println("---");
        }
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
