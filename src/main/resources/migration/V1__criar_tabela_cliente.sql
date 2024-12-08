-- Criar tabelas
CREATE TABLE usuario (
    id_usuario TEXT PRIMARY KEY,
    nome_usuario VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL, -- 'ADMIN' ou 'CLIENTE'
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    fk_usuario TEXT REFERENCES usuario(id_usuario) NOT NULL,
    nome_cliente VARCHAR(255) NOT NULL,
    contato VARCHAR(50),
    endereco TEXT,
    status BOOLEAN DEFAULT TRUE,
    usuario_criacao TEXT REFERENCES usuario(id_usuario) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_atualizacao TEXT REFERENCES usuario(id_usuario) NOT NULL,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE produto (
    id_produto SERIAL PRIMARY KEY,
    nome_produto VARCHAR(255) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade_em_estoque INT NOT NULL,
    usuario_criacao TEXT REFERENCES usuario(id_usuario) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_atualizacao TEXT REFERENCES usuario(id_usuario) NOT NULL,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pedido (
    id_pedido SERIAL PRIMARY KEY,
    fk_cliente INT REFERENCES cliente(id_cliente),
    status VARCHAR(50) NOT NULL,
    usuario_criacao TEXT REFERENCES usuario(id_usuario) NOT NULL,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_atualizacao TEXT REFERENCES usuario(id_usuario) NOT NULL,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE item_pedido (
    id_item_pedido SERIAL PRIMARY KEY,
    fk_pedido INT REFERENCES pedido(id_pedido),
    fk_produto INT REFERENCES produto(id_produto),
    quantidade INT NOT NULL,
    preco_por_unidade DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    usuario_criacao TEXT REFERENCES usuario(id_usuario) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_atualizacao TEXT REFERENCES usuario(id_usuario) NOT NULL,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
