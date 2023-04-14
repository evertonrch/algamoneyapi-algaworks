CREATE TABLE tb_pessoa (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    ativo VARCHAR(10) NOT NULL,
    logradouro VARCHAR(60),
    numero VARCHAR(10),
    complemento VARCHAR(100),
    bairro VARCHAR(50),
    cep VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;