# Sistema de Gerenciamento de Restaurante

## Descrição

Sistema de console em Java para gerenciar reservas de mesas, pedidos, pagamentos, clientes e produtos de um restaurante, desenvolvido com a arquitetura **Model-View-Controller (MVC)**.

As principais classes de modelo (`Cliente`, `Mesa`, `Produto`, `Reserva`, `Pedido`, `Pagamento`) possuem suas regras de negócio especificadas formalmente com **Java Modeling Language (JML)**, usando `requires`, `ensures`, `invariant`, `assignable`, `\old`, `\result` e `pure`.

---

## Objetivos

- Aplicar a arquitetura MVC
- Aplicar conceitos de Programação Orientada a Objetos
- Especificar formalmente regras de negócio utilizando JML
- Desenvolver um sistema organizado e de fácil manutenção

---

## Como executar

Pré-requisitos: **JDK 17+** instalado (o projeto usa recursos como `switch` com expressão, disponíveis a partir do Java 14+).

### Opção 1 — Gradle Wrapper

```bash
./gradlew run
```

(no Windows: `gradlew.bat run`)

### Opção 2 — Compilando manualmente

```bash
find src/main/java -name "*.java" > sources.txt
javac -d out -encoding UTF-8 @sources.txt
java -cp out Main
```

O sistema abre um menu interativo no terminal; basta seguir as opções numeradas.

---

## Arquitetura

O projeto segue o padrão MVC, organizado sob o pacote `restaurante`:

```
src/main/java/
├── Main.java                     # Ponto de entrada da aplicação
└── restaurante/
    ├── controller/                # Orquestram as operações do sistema
    │   ├── ClienteController.java
    │   ├── MesaController.java
    │   ├── ProdutoController.java
    │   ├── ReservaController.java
    │   ├── PedidoController.java
    │   └── PagamentoController.java
    ├── model/                      # Entidades e regras de negócio (+ JML)
    │   ├── Cliente.java
    │   ├── Funcionario.java
    │   ├── Mesa.java
    │   ├── Produto.java
    │   ├── Reserva.java
    │   ├── ItemPedido.java
    │   ├── Pedido.java
    │   └── Pagamento.java
    ├── view/                       # Menus de console (sem regra de negócio)
    │   ├── MenuPrincipalView.java
    │   ├── ClienteView.java
    │   ├── MesaView.java
    │   ├── ProdutoView.java
    │   ├── ReservaView.java
    │   ├── PedidoView.java
    │   └── PagamentoView.java
    ├── exception/                  # Exceções personalizadas do domínio
    ├── enums/                      # Enumerações do sistema
    └── util/                       # Classes auxiliares (ex.: FormatadorMoeda)
```

### Model

Contém toda a regra de negócio e as especificações JML das principais operações.

### Controller

Intermedia a comunicação entre a View e o Model. Cada Controller mantém a coleção de entidades em memória e expõe as operações do sistema.

Exemplo de fluxo:

```
Cliente solicita uma reserva → ReservaView → ReservaController → Reserva
```

### View

Responsável apenas pela interação com o usuário via console (menus, leitura de entrada e exibição de resultados). Toda a regra de negócio permanece no Model; a View nunca decide se uma operação é válida, apenas repassa a decisão ao Controller/Model e exibe o resultado ou o erro.

---

## Funcionalidades implementadas

### Clientes
- Cadastrar cliente
- Atualizar dados do cliente
- Consultar cliente por ID
- Listar todos os clientes

### Mesas
- Cadastrar mesa (número + capacidade)
- Consultar disponibilidade (listar mesas livres)
- Liberar mesa
- Colocar/retirar mesa de manutenção
- Listar todas as mesas

### Produtos
- Cadastrar produto (nome, categoria, preço)
- Atualizar dados (nome/categoria) e preço
- Definir disponibilidade (ativo/inativo)
- Listar produtos (todos ou apenas disponíveis)

### Reservas
Uma reserva contém: cliente, mesa, data, horário e quantidade de pessoas.

Fluxo da reserva:

```
Cliente solicita reserva
        ↓
Sistema verifica disponibilidade da mesa/horário
        ↓
Reserva criada (AGENDADA)
        ↓
Cliente chega ao restaurante → Check-in
        ↓
Mesa fica OCUPADA e um Pedido é aberto automaticamente
        ↓
Pagamento confirmado
        ↓
Mesa liberada e reserva FINALIZADA
```

Também é possível **confirmar**, **cancelar** (apenas reservas AGENDADAS) ou marcar como **NÃO_APARECEU** (reservas AGENDADAS ou CONFIRMADAS que não deram check-in).

### Pedidos
Aberto automaticamente após o check-in de uma reserva.

- Adicionar item (produto + quantidade)
- Remover item
- Calcular total
- Fechar pedido (exige ao menos um item)

Após fechado, o pedido não sofre mais alterações.

### Pagamentos
Disponível apenas para pedidos **FECHADOS**.

- Calcular total do pedido
- Selecionar forma de pagamento
- Confirmar pagamento

Ao confirmar o pagamento:
- o pedido é marcado como **PAGO**
- a mesa é **liberada**
- a reserva correspondente é **finalizada**

---

## Estados

**Mesa:** `LIVRE` → `OCUPADA` → `LIVRE` (ou `MANUTENCAO`)

**Reserva:** `AGENDADA` → `CONFIRMADA` → `FINALIZADA` | `CANCELADA` | `NAO_APARECEU`

**Pedido:** `ABERTO` → `FECHADO` → `PAGO`

---

## Regras de negócio principais

**Mesas**
- A capacidade deve ser maior que zero.
- Apenas mesas `LIVRE` podem receber check-in (ficar `OCUPADA`).

**Reservas**
- Uma mesa não pode ter duas reservas ativas para a mesma data e horário.
- A quantidade de pessoas não pode ultrapassar a capacidade da mesa.
- Apenas reservas `AGENDADA` podem ser confirmadas ou canceladas.
- Apenas reservas `AGENDADA`/`CONFIRMADA` podem realizar check-in ou ser marcadas como `NAO_APARECEU`.

**Produtos**
- Nome não pode ser vazio.
- Preço deve ser positivo.

**Pedidos**
- Não é permitido adicionar/remover itens após o fechamento.
- O valor total nunca pode ser negativo.
- É necessário ao menos um item antes de fechar o pedido.

**Pagamentos**
- Apenas pedidos `FECHADO` podem ser pagos.
- O valor pago deve corresponder exatamente ao valor total do pedido.

Cada uma dessas regras é validada nos modelos (lançando exceções específicas do pacote `exception`) e documentada formalmente em JML nos comentários das classes de `model`.

---

## Utilização da JML

As classes `Cliente`, `Mesa`, `Produto`, `Reserva`, `ItemPedido`, `Pedido` e `Pagamento` utilizam comentários JML (`/*@ ... @*/`) para especificar formalmente:

- `invariant` — condições que sempre devem ser verdadeiras para o objeto
- `requires` — pré-condições de um método
- `ensures` — pós-condições de um método
- `assignable` — quais campos um método pode modificar
- `signals_only` — quais exceções podem ser lançadas
- `pure` — métodos que não alteram estado (ex.: `calcularTotal`, `calcularSubtotal`, `conflitaCom`)

Por serem comentários de bloco, as especificações não interferem na compilação padrão com `javac`; para verificação formal, é possível utilizar uma ferramenta como o [OpenJML](https://www.openjml.org/).

---

## Tecnologias

- Java 17+
- Java Modeling Language (JML)
- Gradle
- IntelliJ IDEA
- Git / GitHub


