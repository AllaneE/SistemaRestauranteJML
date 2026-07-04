package restaurante.controller;

import restaurante.enums.FormaPagamento;
import restaurante.model.Pagamento;
import restaurante.model.Pedido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe responsável por gerenciar os pagamentos do restaurante.
 */

public class PagamentoController {
    private final List<Pagamento> pagamentos =  new ArrayList<>();
    private int proximoId = 1;

    public double calcularTotal(Pedido pedido) {
        return pedido.calcularTotal();
    }

    public Pagamento processarPagamento(Pedido pedido, FormaPagamento formaPagamento, double valorPago) {
        Pagamento pagamento = new Pagamento(proximoId++, pedido, formaPagamento, valorPago);
        pagamento.confirmarPagamento();
        pagamentos.add(pagamento);
        return pagamento;
    }

    public List<Pagamento> listarPagamentos() {
        return Collections.unmodifiableList(pagamentos);
    }

}
