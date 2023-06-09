CREATE TABLE IF NOT EXISTS tb_contato (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    id_pessoa BIGINT(20) NOT NULL,
    FOREIGN KEY (id_pessoa) REFERENCES tb_pessoa (id)
) ENGINE=InnoDB DEFAULT Charset=utf8;