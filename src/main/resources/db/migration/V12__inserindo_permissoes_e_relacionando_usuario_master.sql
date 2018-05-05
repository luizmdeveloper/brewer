INSERT INTO permissao (nome) VALUES ('ROLE_CADASTRAR_CIDADE');
INSERT INTO permissao (nome) VALUES ('ROLE_CADASTRAR_USUARIO');

INSERT INTO grupo_permissao(codigo_grupo, codigo_permissao) VALUES (
	(SELECT codigo FROM grupo WHERE nome = 'Administrador'),
	(SELECT codigo FROM permissao WHERE nome = 'ROLE_CADASTRAR_CIDADE')
);

INSERT INTO grupo_permissao(codigo_grupo, codigo_permissao) VALUES (
	(SELECT codigo FROM grupo WHERE nome = 'Administrador'),
	(SELECT codigo FROM permissao WHERE nome = 'ROLE_CADASTRAR_USUARIO')
);

INSERT INTO usuario_grupo(codigo_usuario, codigo_grupo) VALUES(
	(SELECT codigo FROM usuario WHERE email = 'admin@brewer.com.br'),
	(SELECT codigo FROM grupo WHERE nome = 'Administrador')
);
