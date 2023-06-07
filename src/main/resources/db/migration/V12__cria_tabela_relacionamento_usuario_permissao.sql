CREATE TABLE tb_usuario_permissao(
    id_usuario BIGINT(20) NOT NULL,
    id_permissao BIGINT(20) NOT NULL,
    PRIMARY KEY(id_usuario, id_permissao),
    FOREIGN KEY(id_usuario) REFERENCES tb_usuario(id),
    FOREIGN KEY(id_permissao) REFERENCES tb_permissao(id)
)ENGINE=InnoDB DEFAULT Charset=utf8;