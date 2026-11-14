# Documentação da API REST - Pizzaria

Web Service REST de uma pizzaria, com **Clientes** e **Pedidos** (um cliente tem vários pedidos), incluindo cancelamento de pedido (ex.: pizza veio errada). Usa as mesmas classes e validações do trabalho SOAP (Cliente e Pedido).

- **URL base:** `http://localhost:8080`
- **Formato:** JSON (`Content-Type: application/json`)
- **Documentação interativa (Swagger UI):** `http://localhost:8080/swagger-ui.html`
- **Especificação OpenAPI:** `http://localhost:8080/v3/api-docs` (cópia estática em `docs/openapi.yaml`)

## Resumo das rotas

| # | Método | Rota | Descrição | Sucesso |
|---|--------|------|-----------|---------|
| 1 | GET | `/clientes` | Listar clientes | 200 |
| 2 | GET | `/clientes/{id}` | Buscar cliente por id | 200 |
| 3 | POST | `/clientes` | Cadastrar cliente | 201 |
| 4 | PUT | `/clientes/{id}` | Alterar cliente | 200 |
| 5 | DELETE | `/clientes/{id}` | Excluir cliente | 204 |
| 6 | GET | `/clientes/{id}/pedidos` | Listar pedidos de um cliente | 200 |
| 7 | GET | `/pedidos` | Listar pedidos | 200 |
| 8 | GET | `/pedidos/{id}` | Buscar pedido por id | 200 |
| 9 | POST | `/pedidos` | Criar pedido | 201 |
| 10 | PUT | `/pedidos/{id}` | Alterar pedido | 200 |
| 11 | PATCH | `/pedidos/{id}/cancelar` | Cancelar pedido | 200 |
| 12 | DELETE | `/pedidos/{id}` | Excluir pedido | 204 |

## Formato padrão de erro

Todas as respostas de erro seguem este corpo:

```json
{
  "status": 404,
  "mensagem": "Cliente 99 não encontrado.",
  "timestamp": "2026-11-13T10:30:00"
}
```

## Modelos

**Cliente**

| Campo | Tipo | Obrigatório | Regra |
|-------|------|-------------|-------|
| id | número | não | Gerado automaticamente (somente leitura) |
| nome | texto | sim | Mínimo de 3 caracteres |
| telefone | texto | sim | Números, parênteses, hífen e espaço (8 a 20 caracteres) |
| endereco | texto | sim | Não pode ser vazio |

**Pedido** (resposta)

| Campo | Tipo | Observação |
|-------|------|------------|
| id | número | Gerado automaticamente |
| cliente | Cliente | Cliente dono do pedido |
| itens | texto | Itens do pedido |
| valorTotal | número | Maior que zero |
| status | texto | `CRIADO` ou `CANCELADO` |
| motivoCancelamento | texto ou null | Preenchido ao cancelar |

---

## Clientes

### 1. Listar clientes
- **Requisição:** `GET /clientes`
- **Corpo:** nenhum
- **Respostas:**
  - **200 OK** — lista de clientes (pode ser vazia)
    ```json
    [
      { "id": 1, "nome": "Joao Silva", "telefone": "(47) 99999-9999", "endereco": "Rua A, 123 - Blumenau" }
    ]
    ```

### 2. Buscar cliente por id
- **Requisição:** `GET /clientes/{id}` (ex.: `/clientes/1`)
- **Corpo:** nenhum
- **Respostas:**
  - **200 OK**
    ```json
    { "id": 1, "nome": "Joao Silva", "telefone": "(47) 99999-9999", "endereco": "Rua A, 123 - Blumenau" }
    ```
  - **404 Not Found** — cliente não existe
    ```json
    { "status": 404, "mensagem": "Cliente 99 não encontrado.", "timestamp": "2026-11-13T10:30:00" }
    ```

### 3. Cadastrar cliente
- **Requisição:** `POST /clientes`
- **Corpo:**
  ```json
  { "nome": "Maria Souza", "telefone": "(47) 98888-7777", "endereco": "Rua das Flores, 45 - Blumenau" }
  ```
- **Respostas:**
  - **201 Created** — cliente criado, com o id gerado
    ```json
    { "id": 2, "nome": "Maria Souza", "telefone": "(47) 98888-7777", "endereco": "Rua das Flores, 45 - Blumenau" }
    ```
  - **400 Bad Request** — nome vazio ou com menos de 3 caracteres, telefone inválido, endereço vazio ou JSON malformado
    ```json
    { "status": 400, "mensagem": "Nome do cliente não pode ser vazio.", "timestamp": "2026-11-13T10:30:00" }
    ```

### 4. Alterar cliente
- **Requisição:** `PUT /clientes/{id}`
- **Corpo:**
  ```json
  { "nome": "Joao S. Silva", "telefone": "(47) 98888-8888", "endereco": "Rua B, 456 - Blumenau" }
  ```
- **Respostas:**
  - **200 OK** — cliente alterado (corpo igual ao do item 2)
  - **400 Bad Request** — dados inválidos
  - **404 Not Found** — cliente não existe

### 5. Excluir cliente
- **Requisição:** `DELETE /clientes/{id}`
- **Corpo:** nenhum
- **Respostas:**
  - **204 No Content** — excluído (sem corpo)
  - **404 Not Found** — cliente não existe
  - **409 Conflict** — cliente possui pedidos e não pode ser excluído
    ```json
    { "status": 409, "mensagem": "Cliente 1 possui pedidos e não pode ser excluído.", "timestamp": "2026-11-13T10:30:00" }
    ```

### 6. Listar pedidos de um cliente
- **Requisição:** `GET /clientes/{id}/pedidos`
- **Corpo:** nenhum
- **Descrição:** demonstra a relação 1:N entre Cliente e Pedido.
- **Respostas:**
  - **200 OK** — lista de pedidos do cliente (pode ser vazia), no formato do item 8
  - **404 Not Found** — cliente não existe

---

## Pedidos

### 7. Listar pedidos
- **Requisição:** `GET /pedidos`
- **Corpo:** nenhum
- **Respostas:**
  - **200 OK** — lista de pedidos, criados e cancelados (pode ser vazia)

### 8. Buscar pedido por id
- **Requisição:** `GET /pedidos/{id}`
- **Corpo:** nenhum
- **Respostas:**
  - **200 OK**
    ```json
    {
      "id": 1,
      "cliente": { "id": 1, "nome": "Joao Silva", "telefone": "(47) 99999-9999", "endereco": "Rua A, 123 - Blumenau" },
      "itens": "2x Pizza Calabresa, 1x Refrigerante",
      "valorTotal": 89.9,
      "status": "CRIADO",
      "motivoCancelamento": null
    }
    ```
  - **404 Not Found** — pedido não existe

### 9. Criar pedido
- **Requisição:** `POST /pedidos`
- **Corpo:**
  ```json
  { "clienteId": 1, "itens": "2x Pizza Calabresa, 1x Refrigerante", "valorTotal": 89.90 }
  ```
- **Regra:** o status inicial é `CRIADO`.
- **Respostas:**
  - **201 Created** — pedido criado (corpo igual ao do item 8)
  - **400 Bad Request** — cliente ausente, itens vazios ou valor menor ou igual a zero
    ```json
    { "status": 400, "mensagem": "Valor total do pedido deve ser maior que zero.", "timestamp": "2026-11-13T10:30:00" }
    ```
  - **404 Not Found** — o `clienteId` informado não existe

### 10. Alterar pedido
- **Requisição:** `PUT /pedidos/{id}`
- **Corpo:**
  ```json
  { "itens": "3x Pizza Calabresa", "valorTotal": 120.00 }
  ```
- **Respostas:**
  - **200 OK** — pedido alterado (corpo igual ao do item 8)
  - **400 Bad Request** — dados inválidos
  - **404 Not Found** — pedido não existe
  - **409 Conflict** — pedido cancelado não pode ser alterado

### 11. Cancelar pedido
- **Requisição:** `PATCH /pedidos/{id}/cancelar`
- **Corpo:**
  ```json
  { "motivo": "Pizza veio errada" }
  ```
- **Respostas:**
  - **200 OK** — pedido cancelado
    ```json
    {
      "id": 1,
      "cliente": { "id": 1, "nome": "Joao Silva", "telefone": "(47) 99999-9999", "endereco": "Rua A, 123 - Blumenau" },
      "itens": "2x Pizza Calabresa, 1x Refrigerante",
      "valorTotal": 89.9,
      "status": "CANCELADO",
      "motivoCancelamento": "Pizza veio errada"
    }
    ```
  - **400 Bad Request** — motivo ausente ou em branco
  - **404 Not Found** — pedido não existe
  - **409 Conflict** — pedido já estava cancelado
    ```json
    { "status": 409, "mensagem": "Pedido 1 já está cancelado.", "timestamp": "2026-11-13T10:30:00" }
    ```

### 12. Excluir pedido
- **Requisição:** `DELETE /pedidos/{id}`
- **Corpo:** nenhum
- **Respostas:**
  - **204 No Content** — excluído (sem corpo)
  - **404 Not Found** — pedido não existe
