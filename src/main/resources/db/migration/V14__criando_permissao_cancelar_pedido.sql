INSERT INTO permissao (nome) VALUES ('ROLE_CANCELAR_PEDIDO');

INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (
	(SELECT codigo FROM grupo WHERE nome = 'Administrador'),
	(SELECT codigo FROM permissao WHERE nome = 'ROLE_CANCELAR_PEDIDO')
);