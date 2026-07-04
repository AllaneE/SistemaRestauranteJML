package restaurante.view;

import restaurante.controller.ClienteController;
import restaurante.model.Cliente;

import java.awt.*;
import java.util.Scanner;

public class ClienteView {
    private final Scanner scanner;
    private final ClienteController clienteController;

    public ClienteView(Scanner scanner, ClienteController clienteController) {
        this.scanner = scanner;
        this.clienteController = clienteController;
    }

    public void exibirMenu(){
        boolean continuar = true;
        while(continuar){
            System.out.println("\n--- Menu Clientes ---");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Atualizar cliente");
            System.out.println("3. Consultar cliente");
            System.out.println("4. Listar clientes");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            switch (lerOpcao()) {
                case 1 -> cadastrar();
                case 2 -> atualizar();
                case 3 -> consultar();
                case 4 -> listar();
                case 0 -> continuar = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrar(){
        try{
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            Cliente cliente = clienteController.cadastrarCliente(nome, telefone);
            System.out.println("Cliente cadastrado com sucesso:\n" + cliente);
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizar(){
        try {
            System.out.print("ID do cliente: ");
            int id = lerOpcao();
            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();
            System.out.print("Novo telefone: ");
            String telefone = scanner.nextLine();
            clienteController.atualizarCliente(id, nome, telefone);
            System.out.println("Cliente atualizado com sucesso.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void consultar(){
        try {
            System.out.print("ID do cliente: ");
            int id = lerOpcao();
            System.out.println(clienteController.consultarCliente(id));
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar(){
        if (clienteController.listarClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente cliente : clienteController.listarClientes()) {
            System.out.println(cliente);
            System.out.println("---");
        }
    }

    private int lerOpcao(){
        try {
            int valor = Integer.parseInt(scanner.nextLine().trim());
            return valor;
        } catch (NumberFormatException e) {
            return -1;
        }
    }


}
