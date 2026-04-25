--INSERT INTO TB_PRODUTO (id_produto, nome, valor) VALUES (1, 'Notebook Gamer', 5500.00);
--INSERT INTO TB_PRODUTO (id_produto, nome, valor) VALUES (2, 'Mouse Sem Fio', 120.00);
--INSERT INTO TB_PRODUTO (id_produto, nome, valor) VALUES (3, 'Monitor 24 Polegadas', 950.00);

ALTER TABLE TB_PRODUTO ADD COLUMN data_cadastro TIMESTAMP;

INSERT INTO tb_categoria (nome) VALUES ('Eletrônicos');
INSERT INTO tb_categoria (nome) VALUES ('Informática');
INSERT INTO tb_categoria (nome) VALUES ('Games');
INSERT INTO tb_categoria (nome) VALUES ('Celulares');
