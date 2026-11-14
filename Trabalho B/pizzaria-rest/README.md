# API REST - Pizzaria (Trabalho B: Documentação de Serviços)

Web Service REST em **Spring Boot 3 / Java 17**, construído sobre a mesma base do trabalho SOAP (classes **Cliente** e **Pedido**, com as mesmas validações e a relação 1:N). Persistência em banco **H2** (em memória) via **JPA/Hibernate**, documentação com **Swagger (springdoc-openapi)**.

## Como rodar no IntelliJ
1. **File → Open** e selecione esta pasta (a que tem o `pom.xml`). Clique em **Trust Project**.
2. Aguarde o Maven baixar as dependências (barra de progresso no canto inferior).
3. Confira em **File → Project Structure → Project** se o SDK é o **JDK 17**.
4. Abra `src/main/java/com/pizzaria/PizzariaApplication.java` e clique no ▶️ ao lado do `main`.
5. Quando aparecer `Started PizzariaApplication` no console, a API está no ar.

> A porta 8080 não pode estar em uso. Se o `Publicador` do trabalho SOAP estiver rodando, pare-o antes (ou mude `server.port` em `application.properties`).

## Endereços
| O que | URL |
|-------|-----|
| **Swagger UI** (documentação interativa) | http://localhost:8080/swagger-ui.html |
| Especificação OpenAPI (JSON) | http://localhost:8080/v3/api-docs |
| Console do banco H2 | http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:pizzaria`, usuário `sa`, senha em branco) |

Ao iniciar, um cliente de exemplo (Joao Silva, id 1) já é cadastrado para facilitar os testes.

## O que entregar (Trabalho B)
1. **Prints do Swagger UI** (`/swagger-ui.html`) mostrando os dois grupos (Clientes e Pedidos) com todas as rotas.
2. **Print de uma rota expandida** (ex.: `POST /pedidos`) mostrando corpo, respostas e descrição.
3. **Prints de testes reais** pelo botão *Try it out*: criar pedido (201), buscar cliente inexistente (404), cancelar pedido (200) e cancelar de novo (409).
4. Os arquivos da pasta `docs/`:
   - `docs/DOCUMENTACAO_API.md`: todas as rotas, métodos, corpos e respostas, em texto.
   - `docs/openapi.yaml`: especificação que pode ser aberta em https://editor.swagger.io ou importada no Postman (*Import*).
5. O código no GitHub.

## Estrutura
```
src/main/java/com/pizzaria/
├── model/        Cliente, Pedido, StatusPedido  (entidades JPA + validações)
├── dto/          PedidoRequest, PedidoAlteracaoRequest, CancelamentoRequest, ErroResposta
├── repository/   ClienteRepository, PedidoRepository  (acesso ao banco, via Spring Data JPA)
├── controller/   ClienteController, PedidoController  (rotas REST + anotações Swagger)
├── exception/    tratamento de erros padronizado (400, 404, 409)
└── config/       OpenApiConfig (título da doc) e CargaInicial (cliente de exemplo)
docs/             documentação em arquivo
```

## Ainda não incluído
Autenticação de usuário e framework de segurança (Spring Security), que fazem parte de outros critérios da disciplina.
