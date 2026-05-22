# Biblioteca CRUD

Projeto de exemplo para o desafio "Desafio Extra - CRUD com Integração" — aplicação Java que implementa CRUD completo para duas entidades relacionadas, persistência com JPA/Hibernate, menu interativo via console e integração com a API ViaCEP.

**Status do repositório**
- Branch principal: `main`
- Entidades principais: `User` e `Book` em [src/main/java/com/biblioteca/model](src/main/java/com/biblioteca/model)
- Serviço de API externa: `ViaCepService` em [src/main/java/com/biblioteca/service](src/main/java/com/biblioteca/service)

**Requisitos rápidos**
- Java 17
- Maven
- MySQL (banco `biblioteca_db` — veja [db/init.sql](db/init.sql))

**Visão geral das funcionalidades**
- CRUD completo para `User` e `Book` (create/read/update/delete) usando JPA/Hibernate.
- Relação entre entidades: as entidades estão relacionadas entre si (ver modelos em [src/main/java/com/biblioteca/model](src/main/java/com/biblioteca/model)).
- Menu interativo via `Scanner` para inserir, listar, atualizar e remover registros.
- Integração com a API ViaCEP (classe `ViaCepService`) para buscar/auto-preencher endereço a partir do CEP.
- Configurações sensíveis lidas via arquivo `.env` (ex.: credenciais do banco).

Setup e execução
-----------------

1) Preparar banco de dados MySQL

- Crie o banco `biblioteca_db` no MySQL.
- Opcionalmente, execute o script [db/init.sql](db/init.sql) para criar o esquema básico.

2) Configurar variáveis de ambiente

Copie o arquivo de exemplo e edite os valores de conexão:

```powershell
copy src\main\resources\.env.example .env
```

No arquivo `.env` defina ao mínimo:

```
DB_HOST=localhost
DB_PORT=3306
DB_NAME=biblioteca_db
DB_USER=seu_usuario
DB_PASS=sua_senha
```

Coloque `.env` em `src/main/resources` ou no diretório raiz do projeto (a aplicação busca nos dois locais).

3) Build e execução

Compile e empacote com Maven (sem testes):

```powershell
mvn -DskipTests package
```

Execute a aplicação (o projeto usa o plugin `exec:java`):

```powershell
mvn -DskipTests exec:java
```

Ou execute o JAR gerado em `target`:

```powershell
java -jar target\biblioteca-crrud-1.0-SNAPSHOT.jar
```

4) Uso (fluxo básico)

- Ao iniciar, o menu interativo permite escolher entre operações para `User` e `Book`.
- Para criar/editar um `User`, siga as instruções e use a opção de buscar endereço por CEP para preencher automaticamente (integração ViaCEP).
- Valide entradas conforme as mensagens exibidas — campos obrigatórios são verificados pela aplicação.

Arquitetura e arquivos importantes
---------------------------------

- Entidades: [src/main/java/com/biblioteca/model](src/main/java/com/biblioteca/model)
- Repositórios/DAO: [src/main/java/com/biblioteca/dao](src/main/java/com/biblioteca/dao)
- Configuração JPA: [src/main/resources/META-INF/persistence.xml](src/main/resources/META-INF/persistence.xml)
- Integração ViaCEP: [src/main/java/com/biblioteca/service/ViaCepService.java](src/main/java/com/biblioteca/service/ViaCepService.java)
- Utilitários de ambiente: [src/main/java/com/biblioteca/util/EnvUtil.java](src/main/java/com/biblioteca/util/EnvUtil.java)

Persistência
------------

O projeto utiliza JPA/Hibernate. A configuração de conexão e propriedades do Hibernate estão em [src/main/resources/META-INF/persistence.xml](src/main/resources/META-INF/persistence.xml). A propriedade `hibernate.hbm2ddl.auto` está configurada para `update`, o que faz com que as tabelas sejam criadas/atualizadas automaticamente.

Integração com API externa
--------------------------

A classe `ViaCepService` consome a API pública ViaCEP para buscar dados de endereço a partir de um CEP e preencher os campos do `User` automaticamente.

Boas práticas e observações
--------------------------

- Não comite o arquivo `.env` com credenciais reais. Utilize `.env.example` para fornecer placeholders.
- Se você reescrever o histórico Git (force-push), avise colaboradores — eles precisarão sincronizar com `git fetch` e `git reset --hard origin/main` ou clonar novamente.

Debug e resolução de problemas
-----------------------------

- Mostrar erros do Maven em detalhe:

```powershell
mvn -e
mvn -X
```

- Verificar status Git:

```powershell
git status
git log --oneline --graph --all
```

Checklist de validação (para submissão)
--------------------------------------

- [ ] CRUD completo para 2 entidades relacionadas (`User` e `Book`)
- [ ] Conexão funcional com MySQL
- [ ] Menu/interação via console funcionando (inserir/atualizar/deletar/listar)
- [ ] Consumo da API ViaCEP funcionando e integrado a uma funcionalidade
- [ ] Arquivo `.env` configurado (não comitado)
- [ ] `README.md` com instruções claras (este arquivo)
- [ ] Commits realizados antes do prazo do desafio

Contato / Autor
---------------

- Repositório: branch `main` neste projeto.
- Autor/contato: ian8791 — ianpinheiro8791@gmail.com

Se quiser, eu posso:
- adicionar um `CONTRIBUTORS.md` com os nomes corretos,
- incluir exemplos de uso passo-a-passo com prints do menu,
- ou abrir um commit/PR com pequenas melhorias de documentação.

Boa sorte e avisa se quer que eu edite algo específico no README.

