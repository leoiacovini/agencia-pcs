
CREATE TABLE cidades (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  estado VARCHAR(255) NOT NULL,
  pais VARCHAR(255) NOT NULL
);

CREATE TABLE hoteis (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  cidade_id INT REFERENCES cidades(id) ON DELETE CASCADE,
  preco DOUBLE NOT NULL,
  CONSTRAINT hoteis_cidade_id_fk
    FOREIGN KEY (cidade_id)
    REFERENCES cidades(id)
    ON DELETE CASCADE
);

CREATE TABLE transportes (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  tipo VARCHAR(255) NOT NULL,
  cidade_partida_id INT NOT NULL REFERENCES cidades(id) ON DELETE CASCADE,
  cidade_chegada_id INT NOT NULL REFERENCES cidades(id) ON DELETE CASCADE,
  preco DOUBLE NOT NULL,
  CONSTRAINT transportes_cidade_partida_id_fk
    FOREIGN KEY (cidade_partida_id)
    REFERENCES cidades(id)
    ON DELETE CASCADE,
  CONSTRAINT trasportes_cidade_chegada_id_fk
    FOREIGN KEY (cidade_chegada_id)
    REFERENCES cidades(id)
    ON DELETE CASCADE
);

CREATE TABLE clientes (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  cpf VARCHAR(255) NOT NULL UNIQUE,
  rg VARCHAR(255) NOT NULL UNIQUE,
  passaporte VARCHAR(255),
  email VARCHAR(255) NOT NULL UNIQUE,
  telefone VARCHAR(255) NOT NULL
);

CREATE TABLE funcionarios (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL UNIQUE,
  encrypted_password VARCHAR(255) NOT NULL,
  cargo VARCHAR(255) NOT NULL
);

CREATE TABLE pagamentos (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  forma VARCHAR(255) NOT NULL,
  codigo_confirmacao VARCHAR(255) NULL,
  valor DOUBLE NOT NULL
);

CREATE TABLE roteiros (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  duracao INT,
  funcionario_id INT NULL REFERENCES funcionarios(id) ON DELETE SET NULL,
  cliente_id INT NOT NULL REFERENCES clientes(id) ON DELETE CASCADE,
  pagamento_id INT REFERENCES pagamentos(id) ON DELETE CASCADE,
  numero_pessoas INT,
  CONSTRAINT roteiros_cliente_fk
    FOREIGN KEY (cliente_id)
    REFERENCES clientes(id)
    ON DELETE CASCADE,
  CONSTRAINT roteiros_funcionario_id_fk
    FOREIGN KEY (funcionario_id)
    REFERENCES funcionarios(id)
    ON DELETE SET NULL,
  CONSTRAINT roteiros_pagamento_id_fk
    FOREIGN KEY (pagamento_id)
    REFERENCES pagamentos(id)
    ON DELETE CASCADE
);

CREATE TABLE trechos (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  is_trecho_inicial BOOLEAN NOT NULL DEFAULT FALSE,
  duracao INT NOT NULL,
  cidade_id INT NOT NULL REFERENCES cidades(id) ON DELETE CASCADE ,
  hotel_id INT NULL REFERENCES hoteis(id) ON DELETE CASCADE ,
  transporte_id INT NULL REFERENCES transportes(id) ON DELETE CASCADE,
  roteiro_id INT NOT NULL REFERENCES roteiros(id) ON DELETE CASCADE,
  CONSTRAINT trechos_cidade_id_fk
    FOREIGN KEY (cidade_id)
    REFERENCES cidades(id)
    ON DELETE CASCADE,
  CONSTRAINT trechos_hotel_id_fk
    FOREIGN KEY (hotel_id)
    REFERENCES hoteis(id)
    ON DELETE CASCADE,
  CONSTRAINT trechos_transporte_id_fk
    FOREIGN KEY (transporte_id)
    REFERENCES transportes(id)
    ON DELETE CASCADE,
  CONSTRAINT trechos_roteiro_id_fk
    FOREIGN KEY (roteiro_id)
    REFERENCES roteiros(id)
    ON DELETE CASCADE
);
