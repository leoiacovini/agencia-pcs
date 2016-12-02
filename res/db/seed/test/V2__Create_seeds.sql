INSERT INTO cidades (nome, estado, pais, id) VALUES ('Sao Paulo', 'SP', 'Brasil', 1);
INSERT INTO cidades (nome, estado, pais, id) VALUES ('Rio de Janeiro', 'RJ', 'Brasil', 2);
INSERT INTO cidades (nome, estado, pais, id) VALUES ('Minas Gerais', 'MG', 'Brasil', 3);
INSERT INTO cidades (nome, estado, pais, id) VALUES ('Guarulhos', 'SP', 'Brasil', 4);
INSERT INTO cidades (nome, estado, pais, id) VALUES ('Paraty', 'RJ', 'Brasil', 5);
INSERT INTO cidades (nome, estado, pais, id) VALUES ('Campos do Jordão', 'SP', 'Brasil', 6);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Ibis', 120.0, 1, 1);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Hilton', 200.0, 2, 2);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Holiday Inn', 100.0, 3, 3);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Ibis', 100.0, 4, 4);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Triade', 100.0, 5, 5);
INSERT INTO hoteis (nome, preco, cidade_id, id) VALUES ('Bella', 100.0, 6, 6);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('avião', 240.0, 4, 5, 1);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('avião', 200.0, 2, 1, 2);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('onibus', 120.0, 1, 2, 3);
INSERT INTO transportes (tipo, preco, cidade_partida_id, cidade_chegada_id, id) VALUES ('onibus', 140.0, 2, 3, 4);

INSERT INTO funcionarios (nome, username, encrypted_password, cargo) VALUES ('Fulano', 'fulano', '46070d4bf934fb0d4b06d9e2c46e346944e322444900a435d7d9a95e6d7435f5', 'agente');
INSERT INTO clientes (nome, cpf, rg, passaporte, email, telefone) VALUES ('Mario', '00312345678', '1234567890', '12309128901', 'cliente@email.com', '991234567');

INSERT INTO pagamentos (forma, codigo_confirmacao, valor) VALUES ('cartao', '123456', 600.0);
INSERT INTO roteiros (duracao, funcionario_id, cliente_id, pagamento_id) VALUES (5, 1, 1, 1);
INSERT INTO trechos (duracao, cidade_id, hotel_id, transporte_id, roteiro_id) VALUES (0, 2, null, 2, 1);
INSERT INTO trechos (duracao, cidade_id, hotel_id, transporte_id, roteiro_id) VALUES (5, 1, 1, 3, 1);