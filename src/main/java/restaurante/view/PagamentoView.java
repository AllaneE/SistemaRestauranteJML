package restaurante.view;

import restaurante.controller.PagamentoController;
import restaurante.controller.PedidoController;
import restaurante.controller.ReservaController;
import restaurante.enums.FormaPagamento;
import restaurante.model.Pagamento;
import restaurante.model.Pedido;
import restaurante.model.Reserva;
import restaurante.util.FormatadorMoeda;

import java.util.Scanner;

public class PagamentoView {
    private final Scanner scanner;
    private final PagamentoController pagamentoController;
    private final PedidoController pedidoController;
    private final ReservaController reservaController;

    public PagamentoView(Scanner scanner, PagamentoController pagamentoController,
                         PedidoController pedidoController, ReservaController reservaController) {
        this.scanner = scanner;
        this.pagamentoController = pagamentoController;
        this.pedidoController = pedidoController;
        this.reservaController = reservaController;
    }

    public void exibirMenu() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Menu Pagamentos ---");
            System.out.println("1. Pagar pedido");
            System.out.println("2. Listar pagamentos");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            switch (lerOpcao()) {
                case 1 -> pagarPedido();
                case 2 -> listar();
                case 0 -> continuar = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void pagarPedido() {
        try {
            System.out.print("ID do pedido: ");
            int idPedido = lerOpcao();
            Pedido pedido = pedidoController.consultarPedido(idPedido);

            double total = pagamentoController.calcularTotal(pedido);
            System.out.println("Total a pagar: " + FormatadorMoeda.Formatador(total));

            FormaPagamento forma = lerFormaPagamento();

            System.out.print("Valor pago: ");
            double valorPago = Double.parseDouble(scanner.nextLine().trim());

            Pagamento pagamento = pagamentoController.processarPagamento(pedido, forma, valorPago);
            System.out.println("Pagamento confirmado com sucesso:\n" + pagamento);

            // Finaliza a reserva associada e libera a mesa (mesa já foi liberada pelo pagamento).
            try {
                Reserva reserva = reservaController.buscarReservaEmAndamentoPorMesa(pedido.getMesa());
                reservaController.finalizarReserva(reserva.getId());
                System.out.println("Reserva #" + reserva.getId() + " finalizada. Mesa " + pedido.getMesa().getNumero() + " liberada.");
            } catch (RuntimeException e) {
                System.out.println("Aviso: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: valor informado é inválido.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        if (pagamentoController.listarPagamentos().isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }
        for (Pagamento pagamento : pagamentoController.listarPagamentos()) {
            System.out.println(pagamento);
            System.out.println("---");
        }
    }

    private FormaPagamento lerFormaPagamento() {
        System.out.println("Formas de pagamento: 1-PIX 2-CARTAO_DEBITO 3-CARTAO_CREDITO 4-DINHEIRO");
        System.out.print("Forma de pagamento: ");
        int opcao = lerOpcao();
        return switch (opcao) {
            case 1 -> FormaPagamento.PIX;
            case 2 -> FormaPagamento.CARTAO_DEBITO;
            case 3 -> FormaPagamento.CARTAO_CREDITO;
            case 4 -> FormaPagamento.DINHEIRO;
            default -> throw new IllegalArgumentException("Forma de pagamento inválida.");
        };
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
