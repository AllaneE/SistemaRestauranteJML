package restaurante.view;

import restaurante.controller.ProdutoController;
import restaurante.enums.CategoriaProduto;
import restaurante.model.Produto;

import java.util.Scanner;

public class ProdutoView {
    private final Scanner scanner;
    private final ProdutoController produtoController;

    public ProdutoView(Scanner scanner, ProdutoController produtoController) {
        this.scanner = scanner;
        this.produtoController = produtoController;
    }

    public void exibirMenu() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Menu Produtos ---");
            System.out.println("1. Cadastrar produto");
            System.out.println("2. Atualizar dados do produto");
            System.out.println("3. Atualizar preço");
            System.out.println("4. Definir disponibilidade");
            System.out.println("5. Listar produtos");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            switch (lerOpcao()) {
                case 1 -> cadastrar();
                case 2 -> atualizarDados();
                case 3 -> atualizarPreco();
                case 4 -> definirDisponibilidade();
                case 5 -> listar();
                case 0 -> continuar = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrar() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            CategoriaProduto categoria = lerCategoria();
            System.out.print("Preço: ");
            double preco = Double.parseDouble(scanner.nextLine().trim());
            Produto produto = produtoController.cadastrarProduto(nome, categoria, preco);
            System.out.println("Produto cadastrado com sucesso:\n" + produto);
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizarDados() {
        try {
            System.out.print("ID do produto: ");
            int id = lerOpcao();
            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();
            CategoriaProduto categoria = lerCategoria();
            produtoController.atualizarProduto(id, nome, categoria);
            System.out.println("Produto atualizado com sucesso.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizarPreco() {
        try {
            System.out.print("ID do produto: ");
            int id = lerOpcao();
            System.out.print("Novo preço: ");
            double preco = Double.parseDouble(scanner.nextLine().trim());
            produtoController.atualizarPreco(id, preco);
            System.out.println("Preço atualizado com sucesso.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void definirDisponibilidade() {
        try {
            System.out.print("ID do produto: ");
            int id = lerOpcao();
            System.out.print("Disponível? (s/n): ");
            boolean disponivel = scanner.nextLine().trim().equalsIgnoreCase("s");
            produtoController.definirDisponivel(id, disponivel);
            System.out.println("Disponibilidade atualizada com sucesso.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        if (produtoController.listarProdutos().isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        for (Produto produto : produtoController.listarProdutos()) {
            System.out.println(produto);
            System.out.println("---");
        }
    }

    private CategoriaProduto lerCategoria() {
        System.out.println("Categorias: 1-ENTRADA 2-PRATO_PRINCIPAL 3-SOBREMESA 4-BEBIDA");
        System.out.print("Categoria: ");
        int opcao = lerOpcao();
        return switch (opcao) {
            case 1 -> CategoriaProduto.ENTRADA;
            case 2 -> CategoriaProduto.PRATO_PRINCIPAL;
            case 3 -> CategoriaProduto.SOBREMESA;
            case 4 -> CategoriaProduto.BEBIDA;
            default -> throw new IllegalArgumentException("Categoria inválida.");
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