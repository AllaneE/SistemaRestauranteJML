package restaurante.model;

import restaurante.enums.StatusPedido;
import restaurante.exception.PedidoInvalidoException;
import restaurante.exception.ValorInvalidoException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    /*@ public invariant id > 0; @*/
    /*@ public invariant cliente != null; @*/
    /*@ public invariant mesa != null; @*/
    /*@ public invariant itens != null; @*/
    /*@ public invariant status != null; @*/

    private final /*@ spec_public @*/ int id;
    private final /*@ spec_public @*/ Cliente cliente;
    private final /*@ spec_public @*/ Mesa mesa;
    private final /*@ spec_public @*/ List<ItemPedido> itens;
    private /*@ spec_public @*/ StatusPedido  status;

    /*@
      @ requires id > 0;
      @ requires cliente != null;
      @ requires mesa != null;
      @ ensures this.id == id;
      @ ensures this.cliente == cliente;
      @ ensures this.mesa == mesa;
      @ ensures this.itens != null && this.itens.isEmpty();
      @ ensures this.status == StatusPedido.ABERTO;
      @*/
    public Pedido(int id, Cliente cliente, Mesa mesa) {
        if (id <= 0) {
            throw new ValorInvalidoException("O ID do pedido deve ser maior que zero.");
        }
        if (cliente == null) {
            throw new ValorInvalidoException("Cliente não pode ser nulo.");
        }
        if (mesa == null) {
            throw new ValorInvalidoException("Mesa não pode ser nula.");
        }
        this.id = id;
        this.cliente = cliente;
        this.mesa = mesa;
        this.itens = new ArrayList<>();
        this.status = StatusPedido.ABERTO;
    }

    /*@ pure @*/
    public int getId() {
        return id;
    }

    /*@ pure @*/
    public Cliente getCliente() {
        return cliente;
    }

    /*@ pure @*/
    public Mesa getMesa() {
        return mesa;
    }

    /*@ pure @*/
    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    /*@ pure @*/
    public StatusPedido getStatus() {
        return status;
    }

    /*@
      @ requires this.status == StatusPedido.ABERTO;
      @ requires produto != null && quantidade > 0 && produto.isAtivo();
      @ assignable this.itens;
      @ ensures this.itens.size() == \old(this.itens.size()) + 1;
      @ signals (PedidoInvalidoException e) (\old(this.status) != StatusPedido.ABERTO);
      @ signals (ValorInvalidoException e)
      @         (\old(this.status) == StatusPedido.ABERTO && (produto == null || quantidade <= 0));
      @ signals (ValorInvalidoException e)
      @         (\old(this.status) == StatusPedido.ABERTO && produto != null && !produto.isAtivo());
      @*/
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

    /*@
      @ requires this.status == StatusPedido.ABERTO;
      @ requires item != null && this.itens.contains(item);
      @ assignable this.itens;
      @ ensures !this.itens.contains(item);
      @ ensures this.itens.size() == \old(this.itens.size()) - 1;
      @ signals (PedidoInvalidoException e) (\old(this.status) != StatusPedido.ABERTO);
      @ signals (ValorInvalidoException e)
      @         (\old(this.status) == StatusPedido.ABERTO && (item == null || !\old(this.itens).contains(item)));
      @*/
    public void removerItem(ItemPedido item) {
        if (status != StatusPedido.ABERTO) {
            throw new PedidoInvalidoException("Não é permitido remover produtos após o fechamento do pedido.");
        }
        if(item == null || !itens.remove(item)) {
            throw new ValorInvalidoException("Item inválido ou não encontrado no pedido.");
        }
    }

    /*@
      @ ensures \result >= 0.0;
      @ ensures \result == (\sum int i; 0 <= i && i < itens.size(); itens.get(i).calcularSubtotal());
      @ pure
      @*/
    public double calcularTotal(){
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    /*@
      @ requires this.status == StatusPedido.ABERTO;
      @ requires !this.itens.isEmpty();
      @ assignable this.status;
      @ ensures this.status == StatusPedido.FECHADO;
      @ signals (PedidoInvalidoException e)
      @         (\old(this.status) != StatusPedido.ABERTO || \old(this.itens.isEmpty()));
      @*/
    public void fecharPedido(){
        if(status != StatusPedido.ABERTO) {
            throw new PedidoInvalidoException("Apenas pedidos ABERTOS podem ser fechados.");
        }
        if(itens.isEmpty()) {
            throw new PedidoInvalidoException("Não é possível fechar um pedido sem itens.");
        }
        status = StatusPedido.FECHADO;
    }

    /*@
      @ requires this.status == StatusPedido.FECHADO;
      @ assignable this.status;
      @ ensures this.status == StatusPedido.PAGO;
      @ signals (PedidoInvalidoException e) (\old(this.status) != StatusPedido.FECHADO);
      @*/
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
