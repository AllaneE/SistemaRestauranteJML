package restaurante.controller;

import restaurante.exception.PedidoNaoEncontradoException;
import restaurante.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PedidoController {
    private final List<Pedido> pedidos = new ArrayList<>();
    private int proximoId = 1;

    public Pedido abrirPedido(Cliente cliente, Mesa mesa) {
        Pedido pedido = new Pedido(proximoId++, cliente, mesa);
        pedidos.add(pedido);
        return pedido;
    }

    public Pedido consultarPedido(int id) {
        for(Pedido pedido : pedidos) {
            if(pedido.getId() == id) {
                return pedido;
            }
        }
        throw new PedidoNaoEncontradoException("Pedido no encontrado");
    }

    public void adcionarItem(int idPedido, Produto produto, int quantidade) {
        consultarPedido(idPedido).addItem(produto, quantidade);
    }

    public void removerItem(int idPedido, ItemPedido item) {
        consultarPedido(idPedido).removerItem(item);
    }

    public double calcularTotal(int idPedido) {
        return consultarPedido(idPedido).calcularTotal();
    }

    public void fecharPedido(int idPedido) {
        consultarPedido(idPedido).fecharPedido();
    }

    public List<Pedido> listarPedidos() {
        return Collections.unmodifiableList(pedidos);
    }
}
