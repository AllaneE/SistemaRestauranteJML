package restaurante.model;

import restaurante.enums.StatusPedido;
import restaurante.exception.PedidoInvalidoException;
import restaurante.exception.ValorInvalidoException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private final int id;
    private final Cliente cliente;
    private final Mesa mesa;
    private final List<ItemPedido> itens;
    private StatusPedido  status;

    public Pedido(int id, Cliente cliente, Mesa mesa) {
        this.id = id;
        this.cliente = cliente;
        this.mesa = mesa;
        this.itens = new ArrayList<>();
        this.status = StatusPedido.ABERTO;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void addItem(Produto produto, int quantidade) {
        if (status != StatusPedido.ABERTO) {
            throw new PedidoInvalidoException("Não é permitido adicionar produtos após o fechamento do pedido.");
        }
        if(produto == null || quantidade <= 0) {
            throw new ValorInvalidoException("Produto ou quantidade inválidos.");
        }
        if(!produto.isAtivo()){
            throw new ValorInvalidoException("Produto inativo não pode ser adicionado ao pedido.");
        }
        itens.add(new ItemPedido(produto, quantidade));
    }

    public void removerItem(ItemPedido item) {
        if (status != StatusPedido.ABERTO) {
            throw new PedidoInvalidoException("Não é permitido remover produtos após o fechamento do pedido.");
        }
        if(item == null || !itens.remove(item)) {
            throw new ValorInvalidoException("Item inválido ou não encontrado no pedido.");
        }
    }

    public double calcularTotal(){
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void fecharPedido(){
        if(status != StatusPedido.ABERTO) {
            throw new PedidoInvalidoException("Apenas pedidos ABERTOS podem ser fechados.");
        }
        if(itens.isEmpty()) {
            throw new PedidoInvalidoException("Não é possível fechar um pedido sem itens.");
        }
        status = StatusPedido.FECHADO;
    }

    public void pedidoPago(){
        if(status != StatusPedido.FECHADO) {
            throw new PedidoInvalidoException("Apenas pedidos FECHADOS podem ser marcados como pagos.");
        }
        status = StatusPedido.PAGO;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(id)
                .append("\nCliente: ").append(cliente.getNome())
                .append("\nMesa: ").append(mesa.getNumero())
                .append("\nStatus: ").append(status)
                .append("\nItens:\n");
        if (itens.isEmpty()) {
            sb.append("  (nenhum item)\n");
        } else {
            for (ItemPedido item : itens) {
                sb.append("  - ").append(item).append("\n");
            }
        }
        sb.append("Total: ").append(restaurante.util.FormatadorMoeda.Formatador(calcularTotal()));
        return sb.toString();
    }
}
