package restaurante.controller;

import restaurante.enums.StatusMesa;
import restaurante.exception.MesaNaoEncontradaException;
import restaurante.model.Mesa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe responsável por gerenciar as mesas do restaurante.
 */

public class MesaController {
    private final List<Mesa> mesas = new ArrayList<>();

    public Mesa CadastroMesa(int numero, int capacidade){
        Mesa mesa = new Mesa(numero, capacidade);
        mesas.add(mesa);
        return mesa;
    }

    public Mesa ConsultarMesa(int numero){
        for (Mesa mesa : mesas){
            if (mesa.getNumero() == numero){
                return mesa;
            }
        }
        throw new MesaNaoEncontradaException("Mesa com número " + numero + " não encontrada.");
    }

    public List<Mesa> ListarMesas(){
        return Collections.unmodifiableList(mesas);
    }

    public List<Mesa> listarDisponiveis(){
        List<Mesa> disponiveis = new ArrayList<>();
        for (Mesa mesa : mesas){
            if(mesa.getStatus() == StatusMesa.LIVRE){
                disponiveis.add(mesa);
            }
        }
        return disponiveis;
    }

    public void liberarMesa(int numero){
        Mesa mesa = ConsultarMesa(numero);
        mesa.desocuparMesa();
    }

    public void colocarEmManutencao(int numero){
        Mesa mesa = ConsultarMesa(numero);
        mesa.emManutencao();
    }

    public void retirarMesa(int numero){
        Mesa mesa = ConsultarMesa(numero);
        mesa.retirarManutencao();
    }

}
