-- V2__insert_admin_user.sql
INSERT INTO usuario (
    nome_usuario, 
    email, 
    senha, 
    tipo, 
    data_criacao, 
    data_atualizacao
) VALUES (
    'Administrador', 
    'felipe@teste.com', 
    '$2a$10$huyHmcEQYljIbUH0wG/XDuPKUf5rlBG23vpn42gqpxmakAkD.sTmG', 
    'ADMIN', 
    '2024-12-10 20:41:49.009115', 
    '2024-12-10 20:41:49.009115'
);