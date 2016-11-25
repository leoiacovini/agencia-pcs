# AgenciaPCS

## Rodando Localmente

Para rodar localmente basta executar o arquivo Main. A aplicação subira em um servidor TomCat embedded, sem a necessidade de ter o TomCat instalado.
(obs: No arquivo Main.java, é possível configurar entre o ambiente `test` ou `dev`, usando o `test` a aplicação usará um banco de dados H2 em memoria, já
o `dev` irá tentar fazer a conexão com um banco de dados MariaDB, assim como especificado no application.conf)

### Banco de Dados

Para rodar localmente, é disponibilizado um shell script para gerenciamento de um container Docker rodando o MariaDB. Os comandos são os seguintes:

- `./dbcli setup` -> cria uma nova instancia do banco de dados, deletando qualquer outra já existente

- `./dbcli start` -> inicia um container parado

- `./dbcli stop` -> para um container

- `./dbcli delete` -> deleta qualquer container existente rodando o BD


## Testes

Para rodar os testes, a aplicação utiliza um servidor TomCat embedded (se necessário) e um banco de dados H2 em memória. Os testes podem ser executados atráves de uma IDE, ou
a utilizando `mvn test`.


## Deploy .war

Para gerar um pacote `.war`, para deploy em servidor JavaEE, baste executar `mvn package`.
