package com.felipeAlves.ecommerce_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipeAlves.ecommerce_api.repository.UsuarioRepository;
import com.felipeAlves.ecommerce_api.security.TokenService;
import com.felipeAlves.ecommerce_api.user.AuthenticationDTO;
import com.felipeAlves.ecommerce_api.user.LoginResponseDTO;
import com.felipeAlves.ecommerce_api.user.Usuario;
import com.felipeAlves.ecommerce_api.user.registerDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TokenService tokenservice;
	
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        var token = tokenservice.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid registerDTO data)throws Exception{
    	if(this.repository.findBylogin(data.login())!=null) {
    		return ResponseEntity.badRequest().build();
    	}
    			
    	String senhaEncriptada = new BCryptPasswordEncoder().encode(data.password());
    	Usuario user = new  Usuario(data.login(), data.email() , senhaEncriptada, data.tipo());

    	this.repository.save(user);
    	
    	return ResponseEntity.ok().build();
    }
}