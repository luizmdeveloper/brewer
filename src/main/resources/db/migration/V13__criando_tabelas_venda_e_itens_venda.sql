CREATE TABLE venda(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_usuario BIGINT(20) NOT NULL,
	codigo_cliente BIGINT(20) NOT NULL,
	data_criacao DATETIME,
	status VARCHAR(30) NOT NULL,
	valor_frete DECIMAL(10, 2),
	valor_desconto DECIMAL(10, 2),
	valor_total DECIMAL(10, 2),
	observacao VARCHAR(200),
	data_hora_entrega DATETIME,
	FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo)
)ENGINE=InnoDB DEFAULT charset=utf8;

CREATE TABLE item_venda(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_venda BIGINT(20) NOT NULL,
	codigo_cerveja BIGINT(20) NOT NULL,
	valor_unitario DECIMAL(10,2) NOT NULL,
	quantidade INTEGER NOT NULL,
	FOREIGN KEY (codigo_venda) REFERENCES venda(codigo),
	FOREIGN KEY (codigo_cerveja) REFERENCES cerveja(codigo)	
)ENGINE=InnoDB DEFAULT charset=utf8;