package model;

import org.junit.jupiter.api.Test;
import restaurante.exception.ValorInvalidoException;
import restaurante.model.Cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClienteTest {

    @Test
    void deveCriarClienteValido() {
        Cliente cliente = new Cliente(1, "Joao Silva", "83999999999");
        assertEquals(1, cliente.getId());
        assertEquals("Joao Silva", cliente.getNome());
        assertEquals("83999999999", cliente.getTelefone());
    }

    @Test
    void naoDeveAceitarIdInvalido() {
        assertThrows(ValorInvalidoException.class, () -> new Cliente(0, "Joao", "83999999999"));
        assertThrows(ValorInvalidoException.class, () -> new Cliente(-1, "Joao", "83999999999"));
    }

    @Test
    void naoDeveAceitarNomeNuloOuVazio() {
        assertThrows(ValorInvalidoException.class, () -> new Cliente(1, null, "83999999999"));
        assertThrows(ValorInvalidoException.class, () -> new Cliente(1, "   ", "83999999999"));
    }

    @Test
    void naoDeveAceitarTelefoneNuloOuVazio() {
        assertThrows(ValorInvalidoException.class, () -> new Cliente(1, "Joao", null));
        assertThrows(ValorInvalidoException.class, () -> new Cliente(1, "Joao", "  "));
    }

    @Test
    void deveAtualizarDadosValidos() {
        Cliente cliente = new Cliente(1, "Joao", "83999999999");
        cliente.atualizarDados("Joao Pedro", "83988888888");
        assertEquals("Joao Pedro", cliente.getNome());
        assertEquals("83988888888", cliente.getTelefone());
    }

    @Test
    void naoDeveAtualizarComNomeVazio() {
        Cliente cliente = new Cliente(1, "Joao", "83999999999");
        assertThrows(ValorInvalidoException.class, () -> cliente.atualizarDados("", "83988888888"));
    }

    @Test
    void naoDeveAtualizarComTelefoneVazio() {
        Cliente cliente = new Cliente(1, "Joao", "83999999999");
        assertThrows(ValorInvalidoException.class, () -> cliente.atualizarDados("Joao Pedro", ""));
    }
}
