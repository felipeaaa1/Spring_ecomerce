package com.felipeAlves.ecommerce_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipeAlves.ecommerce_api.dto.AuthenticationDTO;
import com.felipeAlves.ecommerce_api.dto.LoginResponseDTO;
import com.felipeAlves.ecommerce_api.dto.registerDTO;
import com.felipeAlves.ecommerce_api.model.Usuario;
import com.felipeAlves.ecommerce_api.repository.UsuarioRepository;
import com.felipeAlves.ecommerce_api.security.TokenService;
import com.felipeAlves.ecommerce_api.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TokenService tokenservice;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    UsuarioService usuarioService;
	
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        var token = tokenservice.generateToken((Usuario) auth.getPrincipal());
        
//        SimpleMailMessage mensagem = new SimpleMailMessage();
//        mensagem.setTo("felipe.arnaud.alvves@gmail.com");
//        mensagem.setSubject("Confirmação de Cadastro");
//        mensagem.setText("segue token de login: " );
//        mailSender.send(mensagem);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid registerDTO data)throws Exception{
    		usuarioService.salvar(data);
    	return ResponseEntity.ok().build();
    }
}