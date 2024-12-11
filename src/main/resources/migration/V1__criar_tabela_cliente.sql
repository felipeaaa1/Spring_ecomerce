-- Criar tabelas
CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY, 
    nome_usuario VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL, 
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    fk_usuario INT REFERENCES usuario(id_usuario) NOT NULL,
    nome_cliente VARCHAR(255) NOT NULL,
    contato VARCHAR(50),
    endereco TEXT,
    status BOOLEAN DEFAULT TRUE,
    usuario_criacao INT REFERENCES usuario(id_usuario) NOT NULL, 
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_atualizacao INT REFERENCES usuario(id_usuario) NOT NULL, 
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE produto (
    id_produto SERIAL PRIMARY KEY,
    nome_produto VARCHAR(255) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade_em_estoque INT NOT NULL,
    usuario_criacao INT REFERENCES usuario(id_usuario) NOT NULL, 
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_atualizacao INT REFERENCES usuario(id_usuario) NOT NULL, 
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pedido (
    id_pedido SERIAL PRIMARY KEY,
    fk_cliente INT REFERENCES cliente(id_cliente),
    status VARCHAR(50) NOT NULL,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2),
    usuario_criacao INT REFERENCES usuario(id_usuario) NOT NULL, 
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_atualizacao INT REFERENCES usuario(id_usuario) NOT NULL,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE item_pedido (
    id_item_pedido SERIAL PRIMARY KEY, 
    fk_pedido INT NOT NULL REFERENCES pedido(id_pedido),
    fk_produto INT NOT NULL REFERENCES produto(id_produto),
    quantidade INT NOT NULL CHECK (quantidade > 0),
    preco_por_unidade DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    usuario_criacao INT REFERENCES usuario(id_usuario) NOT NULL, 
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_atualizacao INT REFERENCES usuario(id_usuario) NOT NULL, 
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE OR REPLACE VIEW relatorio_vendas AS
SELECT 
    p.id_pedido AS id_pedido,
    DATE_TRUNC('day', p.data_pedido) AS periodo,
    SUM(ip.quantidade * ip.preco_por_unidade) AS total_vendas,
    SUM(ip.quantidade) AS produtos_vendidos,
	p.status,
    pr.id_produto,
    pr.nome_produto,
    SUM(ip.quantidade) AS quantidade_vendida,
    SUM(ip.subtotal) AS faturamento_por_produto
FROM 
    pedido p
JOIN 
    item_pedido ip ON p.id_pedido = ip.fk_pedido
JOIN 
    produto pr ON ip.fk_produto = pr.id_produto

GROUP BY 
    p.id_pedido, periodo, pr.id_produto, pr.nome_produto;
