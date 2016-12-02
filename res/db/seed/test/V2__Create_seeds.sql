// Rio de Janeiro
INSERT INTO cidades (nome, estado, pais, id) VALUES ('Rio de Janeiro', 'RJ', 'Brasil', 1);

INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Mercure', 300.0, 1, 1);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Arena Leme Hotel', 220.0, 1, 2);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Pompeu Rio Hotel', 500.0, 1, 3);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Ibis', 290.0, 1, 4);

INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Trem', 110.0, 1, 2, 1);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Trem', 200.0, 1, 4, 2);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Ônibus', 90.0, 1, 2, 3);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 300.0, 1, 2, 4);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 230.0, 1, 3, 5);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 310.0, 1, 4, 6);

// =================================================================================================================
// São Paulo
INSERT INTO cidades (nome, estado, pais, id) VALUES ('Sao Paulo', 'SP', 'Brasil', 2);

INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Hotel Unique', 120.0, 2, 5);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Emiliano Hotel', 280.0, 2, 6);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Luz Plaza Hotel', 450.0, 2, 7);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Gran Estanplaza Berrin', 330.0, 2, 8);

INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Trem', 240.0, 2, 1, 7);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Trem', 110.0, 2, 3, 8);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Ônibus', 70.0, 2, 4, 9);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Ônibus', 100.0, 2, 2, 10);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 240.0, 2, 1, 11);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 360.0, 2, 3, 12);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 290.0, 2, 4, 13);

// =================================================================================================================
// Salvador
INSERT INTO cidades (nome, estado, pais, id) VALUES ('Salvador', 'SP', 'Brasil', 3);

INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Grande Hotel da Barra', 280.0, 3, 9);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Sotero Hotel', 310.0, 3, 10);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Mar Brasil Hotel', 250.0, 3, 11);

INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 440.0, 3, 1, 14);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 290.0, 3, 2, 15);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 380.0, 3, 4, 16);

// =================================================================================================================
// Porto Alegre
INSERT INTO cidades (nome, estado, pais, id) VALUES ('Porto Alegre', 'SP', 'Brasil', 4);

INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Ritter Hotel', 400.0, 4, 12);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Emiliano Hotel', 370.0, 4, 13);

INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Ônibus', 200.0, 4, 2, 17);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 320.0, 4, 1, 18);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 360.0, 4, 2, 19);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('Avião', 250.0, 4, 3, 20);

// =================================================================================================================
// Funcionarios
INSERT INTO funcionarios (nome, username, encrypted_password, cargo) VALUES ('Fulano', 'fulano', '46070d4bf934fb0d4b06d9e2c46e346944e322444900a435d7d9a95e6d7435f5', 'agente');

// =================================================================================================================
// Clientes
INSERT INTO clientes (nome, cpf, rg, passaporte, email, telefone) VALUES ('Mario', '00312345678', '1234567890', '12309128901', 'mario@email.com', '991234567');

// =================================================================================================================
// pagamentos
INSERT INTO pagamentos (forma, codigo_confirmacao, valor) VALUES ('cartao', '123456', 600.0);

// =================================================================================================================
// roteiros
INSERT INTO roteiros (duracao, funcionario_id, cliente_id, pagamento_id) VALUES (5, 1, 1, 1);

// =================================================================================================================
// trechos
INSERT INTO trechos (duracao, cidade_id, hotel_id, transporte_id, roteiro_id) VALUES (0, 2, null, 2, 1);
INSERT INTO trechos (duracao, cidade_id, hotel_id, transporte_id, roteiro_id) VALUES (5, 1, 1, 3, 1);