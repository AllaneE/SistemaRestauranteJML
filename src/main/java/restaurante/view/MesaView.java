package restaurante.view;

import restaurante.controller.MesaController;
import restaurante.model.Mesa;

import java.util.Scanner;

public class MesaView {
    private final Scanner scanner;
    private final MesaController mesaController;

    public MesaView(Scanner scanner, MesaController mesaController) {
        this.scanner = scanner;
        this.mesaController = mesaController;
    }

    public void exibirMenu(){
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Menu Mesas ---");
            System.out.println("1. Cadastrar mesa");
            System.out.println("2. Consultar disponibilidade (listar mesas livres)");
            System.out.println("3. Liberar mesa");
            System.out.println("4. Listar todas as mesas");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            switch (lerOpcao()) {
                case 1 -> cadastrar();
                case 2 -> listarDisponiveis();
                case 3 -> liberar();
                case 4 -> listarTodas();
                case 0 -> continuar = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrar() {
        try {
            System.out.print("Número da mesa: ");
            int numero = lerOpcao();
            System.out.print("Capacidade: ");
            int capacidade = lerOpcao();
            Mesa mesa = mesaController.CadastroMesa(numero, capacidade);
            System.out.println("Mesa cadastrada com sucesso:\n" + mesa);
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarDisponiveis() {
        if (mesaController.listarDisponiveis().isEmpty()) {
            System.out.println("Nenhuma mesa disponível no momento.");
            return;
        }
        for (Mesa mesa : mesaController.listarDisponiveis()) {
            System.out.println(mesa);
            System.out.println("---");
        }
    }

    private void liberar() {
        try {
            System.out.print("Número da mesa: ");
            int numero = lerOpcao();
            mesaController.liberarMesa(numero);
            System.out.println("Mesa liberada com sucesso.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTodas() {
        if (mesaController.ListarMesas().isEmpty()) {
            System.out.println("Nenhuma mesa cadastrada.");
            return;
        }
        for (Mesa mesa : mesaController.ListarMesas()) {
            System.out.println(mesa);
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