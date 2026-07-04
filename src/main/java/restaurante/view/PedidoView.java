package restaurante.view;

import restaurante.controller.PedidoController;
import restaurante.controller.ProdutoController;
import restaurante.model.ItemPedido;
import restaurante.model.Pedido;
import restaurante.model.Produto;
import restaurante.util.FormatadorMoeda;

import java.util.List;
import java.util.Scanner;

public class PedidoView {
    private final Scanner scanner;
    private final PedidoController pedidoController;
    private final ProdutoController produtoController;

    public PedidoView(Scanner scanner, PedidoController pedidoController, ProdutoController produtoController) {
        this.scanner = scanner;
        this.pedidoController = pedidoController;
        this.produtoController = produtoController;
    }

    public void exibirMenu() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Menu Pedidos ---");
            System.out.println("1. Adicionar item ao pedido");
            System.out.println("2. Remover item do pedido");
            System.out.println("3. Calcular total do pedido");
            System.out.println("4. Fechar pedido");
            System.out.println("5. Consultar pedido");
            System.out.println("6. Listar pedidos");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            switch (lerOpcao()) {
                case 1 -> adicionarItem();
                case 2 -> removerItem();
                case 3 -> calcularTotal();
                case 4 -> fecharPedido();
                case 5 -> consultar();
                case 6 -> listar();
                case 0 -> continuar = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void adicionarItem() {
        try {
            System.out.print("ID do pedido: ");
            int idPedido = lerOpcao();
            System.out.print("ID do produto: ");
            int idProduto = lerOpcao();
            Produto produto = produtoController.consultarProduto(idProduto);
            System.out.print("Quantidade: ");
            int quantidade = lerOpcao();
            pedidoController.adcionarItem(idPedido, produto, quantidade);
            System.out.println("Item adicionado com sucesso.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerItem() {
        try {
            System.out.print("ID do pedido: ");
            int idPedido = lerOpcao();
            Pedido pedido = pedidoController.consultarPedido(idPedido);
            List<ItemPedido> itens = pedido.getItens();
            if (itens.isEmpty()) {
                System.out.println("O pedido não possui itens.");
                return;
            }
            for (int i = 0; i < itens.size(); i++) {
                System.out.println(i + " - " + itens.get(i));
            }
            System.out.print("Índice do item a remover: ");
            int indice = lerOpcao();
            if (indice < 0 || indice >= itens.size()) {
                System.out.println("Índice inválido.");
                return;
            }
            pedidoController.removerItem(idPedido, itens.get(indice));
            System.out.println("Item removido com sucesso.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void calcularTotal() {
        try {
            System.out.print("ID do pedido: ");
            int idPedido = lerOpcao();
            double total = pedidoController.calcularTotal(idPedido);
            System.out.println("Total do pedido: " + FormatadorMoeda.Formatador(total));
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void fecharPedido() {
        try {
            System.out.print("ID do pedido: ");
            int idPedido = lerOpcao();
            pedidoController.fecharPedido(idPedido);
            System.out.println("Pedido fechado com sucesso.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void consultar() {
        try {
            System.out.print("ID do pedido: ");
            int idPedido = lerOpcao();
            System.out.println(pedidoController.consultarPedido(idPedido));
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        if (pedidoController.listarPedidos().isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
            return;
        }
        for (Pedido pedido : pedidoController.listarPedidos()) {
            System.out.println(pedido);
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
