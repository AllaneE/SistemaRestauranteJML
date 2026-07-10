package restaurante.controller;

import restaurante.exception.ClienteNaoEncontradoException;
import restaurante.model.Cliente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe responsável por gerenciar as operações relacionadas aos clientes.
 */

public class ClienteController {
    private final List<Cliente> clientes =  new ArrayList<>();
    private int proximoId = 1;

    public Cliente cadastrarCliente(String nome, String telefone) {
        Cliente cliente = new Cliente(proximoId++, nome, telefone);
        clientes.add(cliente);
        return cliente;
    }

    public Cliente consultarCliente(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        throw new ClienteNaoEncontradoException("Cliente com ID " + id + " não encontrado.");
    }

    public Cliente consultarClientePorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new restaurante.exception.ValorInvalidoException("Nome do cliente não pode ser vazio.");
        }
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equalsIgnoreCase(nome.trim())) {
                return cliente;
            }
        }
        throw new ClienteNaoEncontradoException("Cliente com nome \"" + nome + "\" não encontrado.");
    }

    public void atualizarCliente(int id, String novoNome, String novoTelefone) {
        Cliente cliente = consultarCliente(id);
        cliente.atualizarDados(novoNome, novoTelefone);
    }

    public List<Cliente> listarClientes() {
        return Collections.unmodifiableList(clientes);
    }

}
