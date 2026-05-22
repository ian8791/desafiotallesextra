-- Script de criação do banco e tabela para o projeto biblioteca-crrud
-- Ajuste o nome do banco, usuário e charset conforme necessário

CREATE DATABASE IF NOT EXISTS biblioteca_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biblioteca_db;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  cpf VARCHAR(20),
  cep VARCHAR(20),
  street VARCHAR(255),
  city VARCHAR(100),
  state VARCHAR(50),
  neighborhood VARCHAR(100)
);

-- Exemplo de inserção
INSERT INTO users (name, cpf, cep, street, city, state, neighborhood)
VALUES ('Teste', '00000000000', '00000-000', 'Rua Exemplo', 'Cidade', 'SP', 'Centro');
