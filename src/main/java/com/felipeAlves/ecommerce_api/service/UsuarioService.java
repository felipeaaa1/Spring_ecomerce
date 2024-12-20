package com.felipeAlves.ecommerce_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.felipeAlves.ecommerce_api.dto.registerDTO;
import com.felipeAlves.ecommerce_api.exception.EmailInvalidoException;
import com.felipeAlves.ecommerce_api.exception.EmailJaCadastradoException;
import com.felipeAlves.ecommerce_api.exception.UsuarioJaExisteException;
import com.felipeAlves.ecommerce_api.mailService.EmailService;
import com.felipeAlves.ecommerce_api.model.Usuario;
import com.felipeAlves.ecommerce_api.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    public void salvar(registerDTO data) {
        if (this.usuarioRepository.findByLogin(data.login()) != null) {
            throw new UsuarioJaExisteException("Usuário já existe com o login fornecido.");
        }
        
        if (this.usuarioRepository.findByEmail(data.email()) != null) {
            throw new EmailJaCadastradoException("E-mail já está em uso por outro usuário.");
        }

    	var response = emailService.enviarEmail(data.email(), "Confirmação de Cadastro", "Tudo certo com o e-mail do usuario: "+data.login() );
    	
        if (response.getStatusCode() == 202) {

        String senhaEncriptada = new BCryptPasswordEncoder().encode(data.password());
        Usuario user = new Usuario(data.login(), data.email(), senhaEncriptada, data.tipo());
        this.usuarioRepository.save(user);
        }
    	else {
            throw new EmailInvalidoException("Erro criar usuário, certifique-se de que forneceu um e-mail válido");
    	}
    }
}
