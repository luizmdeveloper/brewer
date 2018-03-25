CREATE TABLE estado (
	codigo BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50)NOT NULL,
	sigla VARCHAR(2) NOT NULL
)ENGINE=InnoDB DEFAULT charset=utf8;

CREATE TABLE cidade (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	codigo_estado BIGINT(20) NOT NULL,
	FOREIGN KEY (codigo_estado) REFERENCES estado(codigo)
)ENGINE=InnoDB DEFAULT charset=utf8;

INSERT INTO estado (codigo, nome, sigla) VALUES(1, 'Pernambuco', 'PE');
INSERT INTO estado (codigo, nome, sigla) VALUES(2, 'Bahia', 'BA');
INSERT INTO estado (codigo, nome, sigla) VALUES(3, 'São Paulo', 'SP');
INSERT INTO estado (codigo, nome, sigla) VALUES(4, 'Minas gerais', 'MG');
INSERT INTO estado (codigo, nome, sigla) VALUES(5, 'Rio de janeiro', 'RJ');
INSERT INTO estado (codigo, nome, sigla) VALUES(6, 'Ceará', 'RJ');

INSERT INTO cidade (nome, codigo_estado) VALUES ('Petrolina', 1);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Salgueiro', 1);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Serra talhada', 1);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Juazeiro', 2);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Salvador', 2);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Remanso', 2);
INSERT INTO cidade (nome, codigo_estado) VALUES ('São paulo', 3);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Santo andré', 3);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Osasco', 3);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Belo horizonte', 4);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Uberlâania', 4);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Juiz de fora', 4);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Rio de janeiro', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Petrópilis', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('São Gonçalo', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Juazeiro do norte', 6);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Crato', 6);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Barbalha', 6);
