package restaurante.view;

import restaurante.controller.*;

import java.util.Scanner;

public class MenuPrincipalView {
    private final Scanner scanner;
    private final ClienteView clienteView;
    private final MesaView mesaView;
    private final ProdutoView produtoView;
    private final ReservaView reservaView;
    private final PedidoView pedidoView;
    private final PagamentoView pagamentoView;

    public MenuPrincipalView(Scanner scanner) {
        this.scanner = scanner;

        ClienteController clienteController = new ClienteController();
        MesaController mesaController = new MesaController();
        ProdutoController produtoController = new ProdutoController();
        ReservaController reservaController = new ReservaController();
        PedidoController pedidoController = new PedidoController();
        PagamentoController pagamentoController = new PagamentoController();

        this.clienteView = new ClienteView(scanner, clienteController);
        this.mesaView = new MesaView(scanner, mesaController);
        this.produtoView = new ProdutoView(scanner, produtoController);
        this.reservaView = new ReservaView(scanner, reservaController, clienteController, mesaController, pedidoController);
        this.pedidoView = new PedidoView(scanner, pedidoController, produtoController);
        this.pagamentoView = new PagamentoView(scanner, pagamentoController, pedidoController, reservaController);
    }

    public void iniciar() {
        System.out.println("=================================================");
        System.out.println(" Sistema de Gerenciamento de Restaurante");
        System.out.println("=================================================");

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Clientes");
            System.out.println("2. Mesas");
            System.out.println("3. Produtos");
            System.out.println("4. Reservas");
            System.out.println("5. Pedidos");
            System.out.println("6. Pagamentos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            switch (lerOpcao()) {
                case 1 -> clienteView.exibirMenu();
                case 2 -> mesaView.exibirMenu();
                case 3 -> produtoView.exibirMenu();
                case 4 -> reservaView.exibirMenu();
                case 5 -> pedidoView.exibirMenu();
                case 6 -> pagamentoView.exibirMenu();
                case 0 -> {
                    continuar = false;
                    System.out.println("Encerrando o sistema. Até logo!");
                }
                default -> System.out.println("Opção inválida.");
            }
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
