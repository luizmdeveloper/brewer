CREATE TABLE cliente (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(80) NOT NULL,
	tipo_pessoa VARCHAR(15),
	cpf_cnpj VARCHAR(30),
	telefone VARCHAR(20),
	email VARCHAR(50) NOT NULL,
	logradouro VARCHAR(50),
	complemento VARCHAR(30),
	numero VARCHAR(10),
	cep VARCHAR(15),
	codigo_cidade BIGINT(20),
	FOREIGN KEY (codigo_cidade) REFERENCES cidade(codigo)
)ENGINE=InnoDB DEFAULT charset=utf8;