# Biblioteca CRUD

Projeto de exemplo para o desafio: CRUD com 2 entidades, JPA/Hibernate, integração ViaCEP e menu interativo.

Requisitos rápidos:
- Java 17
- Maven
- MySQL rodando com banco `biblioteca_db` (veja `db/init.sql`)

Configuração:
1. Copie `.env.example` para `.env` em `src/main/resources` ou no diretório raiz do projeto e ajuste `DB_USER`/`DB_PASS`.
2. Rode o projeto:

```powershell
mvn -DskipTests package
mvn -DskipTests exec:java
```

Funcionalidades:
- CRUD para `User` e `Book` via JPA
- Menu interativo via `Scanner`
- Integração com ViaCEP para preencher endereço por CEP

Observações:
- Tabelas serão criadas/atualizadas automaticamente via `hibernate.hbm2ddl.auto=update`.
- Confirme que o MySQL está acessível com as credenciais do `.env`.
