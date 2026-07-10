package controller;

import org.junit.jupiter.api.Test;
import restaurante.controller.ClienteController;
import restaurante.exception.ClienteNaoEncontradoException;
import restaurante.exception.ValorInvalidoException;
import restaurante.model.Cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClienteControllerTest {

    @Test
    void deveCadastrarECconsultarClientePorId() {
        ClienteController controller = new ClienteController();
        Cliente cliente = controller.cadastrarCliente("Joao Silva", "83999999999");
        assertEquals(cliente, controller.consultarCliente(cliente.getId()));
    }

    @Test
    void deveConsultarClientePorNome() {
        ClienteController controller = new ClienteController();
        Cliente cliente = controller.cadastrarCliente("Joao Silva", "83999999999");
        assertEquals(cliente, controller.consultarClientePorNome("Joao Silva"));
    }

    @Test
    void buscaPorNomeDeveSerCaseInsensitiveEIgnorarEspacos() {
        ClienteController controller = new ClienteController();
        Cliente cliente = controller.cadastrarCliente("Joao Silva", "83999999999");
        assertEquals(cliente, controller.consultarClientePorNome("  joao silva  "));
    }

    @Test
    void deveLancarExcecaoAoBuscarNomeInexistente() {
        ClienteController controller = new ClienteController();
        controller.cadastrarCliente("Joao Silva", "83999999999");
        assertThrows(ClienteNaoEncontradoException.class, () -> controller.consultarClientePorNome("Maria"));
    }

    @Test
    void naoDeveBuscarComNomeVazio() {
        ClienteController controller = new ClienteController();
        assertThrows(ValorInvalidoException.class, () -> controller.consultarClientePorNome(""));
        assertThrows(ValorInvalidoException.class, () -> controller.consultarClientePorNome(null));
    }

    @Test
    void deveLancarExcecaoAoConsultarIdInexistente() {
        ClienteController controller = new ClienteController();
        assertThrows(ClienteNaoEncontradoException.class, () -> controller.consultarCliente(99));
    }
}
