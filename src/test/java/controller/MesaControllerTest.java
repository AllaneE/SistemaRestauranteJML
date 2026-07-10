package controller;

import org.junit.jupiter.api.Test;
import restaurante.controller.MesaController;
import restaurante.exception.MesaJaCadastradaException;
import restaurante.exception.MesaNaoEncontradaException;
import restaurante.exception.ValorInvalidoException;
import restaurante.model.Mesa;

import static org.junit.jupiter.api.Assertions.*;

class MesaControllerTest {

    @Test
    void deveCadastrarMesaComNumeroUnico() {
        MesaController controller = new MesaController();
        Mesa mesa = controller.CadastroMesa(1, 4);
        assertEquals(1, mesa.getNumero());
        assertEquals(1, controller.ListarMesas().size());
    }

    @Test
    void naoDevePermitirDuasMesasComOMesmoNumero() {
        MesaController controller = new MesaController();
        controller.CadastroMesa(1, 4);
        assertThrows(MesaJaCadastradaException.class, () -> controller.CadastroMesa(1, 6));
        assertEquals(1, controller.ListarMesas().size());
    }

    @Test
    void naoDeveCadastrarMesaComCapacidadeInvalida() {
        MesaController controller = new MesaController();
        assertThrows(ValorInvalidoException.class, () -> controller.CadastroMesa(1, 0));
        assertThrows(ValorInvalidoException.class, () -> controller.CadastroMesa(1, -1));
    }

    @Test
    void deveLancarExcecaoAoConsultarMesaInexistente() {
        MesaController controller = new MesaController();
        assertThrows(MesaNaoEncontradaException.class, () -> controller.ConsultarMesa(99));
    }

    @Test
    void deveListarApenasMesasLivres() {
        MesaController controller = new MesaController();
        Mesa m1 = controller.CadastroMesa(1, 4);
        controller.CadastroMesa(2, 2);
        m1.ocuparMesa();
        assertEquals(1, controller.listarDisponiveis().size());
    }
}

