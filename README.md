# 🛍️ Spring E-Commerce API

## 🌐 Visão Geral do Projeto

Projeto E-Commerce API
Visão Geral
Este é um projeto de API REST para um sistema de e-commerce, desenvolvido utilizando o framework Spring Boot. A aplicação oferece funcionalidades completas de backend, incluindo:

Autenticação de usuários com JWT
Gerenciamento de produtos, pedidos e clientes
Envio de e-mails transacionais via SendGrid
Geração de relatórios em formato CSV
Documentação da API com OpenAPI/Swagger

### 🚀 Tecnologias Principais
- **Java**: Versão 21
- **Spring Boot**: Versão 3.4.0
- **Banco de Dados**: PostgreSQL
- **Gerenciamento de Dependências**: Maven

### 🔧 Principais Recursos
- Autenticação JWT
- Controle de Usuários e Permissões
- Gestão de Produtos e Pedidos
- Integração com SendGrid para E-mails
- Geração de Relatórios em CSV
- Documentação de API com OpenAPI/Swagger

## 📦 Dependências Chave
- Spring Data JPA
- Spring Security
- Flyway Migration
- SendGrid
- OpenCSV
- Auth0 JWT
- java-dotenv

## 💽 Migração de Banco de Dados
### Estrutura de Migração
- **V1**: Criação inicial das tabelas
- **V2**: Insert de usuários (em desenvolvimento)

## 🔐 Configuração de Segurança

### 🚪 Registro de Usuário
> **Nota Temporária**: Endpoint de registro temporariamente aberto

#### Exemplo de Registro
```bash
curl --location 'http://localhost:8080/auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "login": "adm",
    "email": "teste@email.com",
    "password": "senha",
    "tipo": "ADMIN"
}'
```

### 🛡️ Configurações de Segurança
- Autenticação baseada em JWT
- Controle de acesso por roles
- Endpoint de registro temporariamente público


## 💸 Teste de Pagamento

Para simular o processo de pagamento, foi criado um código Python em Flask que recebe as requisições com o id_pedido e valor_total, e retorna um status de "Pagamento Efetuado" se o valor for maior que 0.

```bash
from flask import Flask, request, jsonify
import logging

# Configuração do logger
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

app = Flask(__name__)

@app.route('/api/pagamento', methods=['POST'])
def validar_pagamento():
    data = request.json

    # Log da requisição recebida
    logging.info(f"Requisição recebida: {data}")

    # Validação dos campos do JSON
    if "pedido_id" in data and "valor_total" in data:
        if data["valor_total"] > 0:
            return jsonify({
                "status": "Pagamento Efetuado"
            }), 202
        elif data["valor_total"] == 0:
            return jsonify({
                "status": "Pagamento Não Efetuado"
            }), 402
        else:
            return jsonify({"erro": "Valor total inválido"}), 400
    
    return jsonify({"erro": "Dados inválidos"}), 400

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
```

#### Exemplo de Registro
```bash
curl --location 'http://localhost:5000/api/pagamento' \
--header 'Content-Type: application/json' \
--data '{"valor_total": 113212,"pedido_id": 1}'
```

## 🔗 Links Úteis
- **Repositório**: [Spring_ecomerce](https://github.com/felipeaaa1/Spring_ecomerce)
- **Documentação da API**: `http://localhost:8080/swagger-ui.html`

## 📋 Próximos Passos
- [ ] Revisar migração V2
- [ ] Ajustar configurações de segurança
- [ ] Implementar validações adicionais
- [ ] Dockerizar aplicação

## 📧 Contato
[linkedin
](https://www.linkedin.com/in/felipe-alves-3223191bb/)

