package model;

import org.junit.jupiter.api.Test;
import restaurante.enums.StatusMesa;
import restaurante.exception.ValorInvalidoException;
import restaurante.model.Mesa;

import static org.junit.jupiter.api.Assertions.*;

class MesaTest {

    @Test
    void deveCriarMesaValidaComStatusLivre() {
        Mesa mesa = new Mesa(1, 4);
        assertEquals(1, mesa.getNumero());
        assertEquals(4, mesa.getCapacidade());
        assertEquals(StatusMesa.LIVRE, mesa.getStatus());
    }

    @Test
    void naoDeveAceitarCapacidadeZero() {
        assertThrows(ValorInvalidoException.class, () -> new Mesa(1, 0));
    }

    @Test
    void naoDeveAceitarCapacidadeNegativa() {
        assertThrows(ValorInvalidoException.class, () -> new Mesa(1, -3));
    }

    @Test
    void naoDeveAceitarNumeroZeroOuNegativo() {
        assertThrows(ValorInvalidoException.class, () -> new Mesa(0, 4));
        assertThrows(ValorInvalidoException.class, () -> new Mesa(-2, 4));
    }

    @Test
    void deveOcuparMesaLivre() {
        Mesa mesa = new Mesa(1, 4);
        mesa.ocuparMesa();
        assertEquals(StatusMesa.OCUPADA, mesa.getStatus());
    }

    @Test
    void naoDeveOcuparMesaJaOcupada() {
        Mesa mesa = new Mesa(1, 4);
        mesa.ocuparMesa();
        assertThrows(ValorInvalidoException.class, mesa::ocuparMesa);
    }

    @Test
    void naoDeveOcuparMesaEmManutencao() {
        Mesa mesa = new Mesa(1, 4);
        mesa.emManutencao();
        assertThrows(ValorInvalidoException.class, mesa::ocuparMesa);
    }

    @Test
    void deveDesocuparMesaOcupada() {
        Mesa mesa = new Mesa(1, 4);
        mesa.ocuparMesa();
        mesa.desocuparMesa();
        assertEquals(StatusMesa.LIVRE, mesa.getStatus());
    }

    @Test
    void naoDeveDesocuparMesaQueNaoEstaOcupada() {
        Mesa mesa = new Mesa(1, 4);
        assertThrows(ValorInvalidoException.class, mesa::desocuparMesa);
    }

    @Test
    void deveColocarEmManutencaoSomenteSeLivre() {
        Mesa mesa = new Mesa(1, 4);
        mesa.emManutencao();
        assertEquals(StatusMesa.MANUTENCAO, mesa.getStatus());
    }

    @Test
    void naoDeveColocarEmManutencaoSeOcupada() {
        Mesa mesa = new Mesa(1, 4);
        mesa.ocuparMesa();
        assertThrows(ValorInvalidoException.class, mesa::emManutencao);
    }

    @Test
    void deveRetirarDeManutencao() {
        Mesa mesa = new Mesa(1, 4);
        mesa.emManutencao();
        mesa.retirarManutencao();
        assertEquals(StatusMesa.LIVRE, mesa.getStatus());
    }

    @Test
    void naoDeveRetirarDeManutencaoSeNaoEstaEmManutencao() {
        Mesa mesa = new Mesa(1, 4);
        assertThrows(ValorInvalidoException.class, mesa::retirarManutencao);
    }
}

