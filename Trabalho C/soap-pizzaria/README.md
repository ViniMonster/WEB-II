# Web Service SOAP - Pizzaria (Cliente / Pedido) — versão Maven / JDK 17

O JDK 11+ removeu o `javax.jws` e o `javax.xml.ws` que eram nativos até o
JDK 10. Este projeto usa Maven para trazer essas mesmas APIs de volta como
bibliotecas, então funciona normal no seu JDK 17, sem precisar instalar
JDK 8 à parte.

## Como abrir no IntelliJ

1. **File → Open** e selecione a pasta `soap-pizzaria-maven` (a que contém
   o `pom.xml`).
2. O IntelliJ reconhece como projeto Maven automaticamente e pergunta se
   quer importar — clique em **Load Maven Project** (ou no ícone do Maven
   que aparece no canto, "importar alterações").
3. Ele vai baixar sozinho as 3 dependências do `pom.xml`
   (`javax.jws-api`, `jaxws-api`, `jaxws-rt`). Pode levar um minutinho na
   primeira vez.
4. Confirme em **File → Project Structure → Project** que o SDK do projeto
   é o JDK 17 que você já tem.

## Rodando o serviço

Abra `src/main/java/soap/publisher/Publicador.java` e clique no ▶️ ao lado
do método `main` (ou botão direito → Run 'Publicador.main()').

Se aparecer no console:
```
Web Services SOAP publicados com sucesso!
WSDL Cliente: http://localhost:8080/ws/cliente?wsdl
WSDL Pedido : http://localhost:8080/ws/pedido?wsdl
```
está no ar. Abra as duas URLs no navegador — isso é o print do item (g)
do enunciado (**print do WSDL gerado**).

## Cliente de teste

Não precisa gerar stubs nem usar `wsimport`. As interfaces `ClienteWS` e
`PedidoWS` espelham as operações do WSDL, e `ClienteTeste` conecta nas duas
URLs `?wsdl`.

1. Rode o `Publicador` e deixe ele ligado.
2. Rode `soap.cliente.ClienteTeste` (outra configuração de execução).
3. O console mostra inserir, consultar, alterar, listar, a relação 1:N
   (`listarPedidosPorCliente`), os erros de validação e as exclusões. Esse
   console é o print do item (h): **prints do Web Service funcionando**.

## Subindo no GitHub

```
/webservice   -> src/main/java/soap/model, controle, service, publisher + pom.xml
/cliente      -> src/main/java/soap/cliente (+ stubs gerados, opcional)
```

## Checklist de entregáveis
- [ ] Código-fonte do projeto Web Service
- [ ] Código-fonte da aplicação cliente
- [ ] Prints mostrando o Web Service funcionando
- [ ] Print do WSDL gerado
- [ ] Tudo versionado no GitHub

## Onde está a relação entre as 2 classes
`Pedido` guarda `clienteId`, e `PedidoControle.listarPorCliente(clienteId)`
— exposto como a operação SOAP `listarPedidosPorCliente` — é a prova
funcional da relação 1:N entre Cliente e Pedido.
