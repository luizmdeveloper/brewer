CREATE TABLE estilo(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT charset=utf8;


CREATE TABLE cerveja(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	sku VARCHAR(50) NOT NULL,
	nome VARCHAR(40) NOT NULL,	
	descricao TEXT NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
	teor_alcoolico DECIMAL(10,2) NOT NULL,
	comissao DECIMAL(10,2) NOT NULL,
	sabor VARCHAR(50) NOT NULL,
	origem VARCHAR(50) NOT NULL,
	codigo_estilo BIGINT(20) NOT NULL,
	FOREIGN KEY(codigo_estilo) REFERENCES estilo(codigo)	
)ENGINE=InnoDB DEFAULT charset=utf8;

INSERT INTO estilo(nome) VALUES("Amber lager");
INSERT INTO estilo(nome) VALUES("Dark lager");
INSERT INTO estilo(nome) VALUES("Pale lager");
INSERT INTO estilo(nome) VALUES("Pilsner");