package com.felipeAlves.ecommerce_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @OneToOne(optional = false)
    @JoinColumn(name = "fk_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, length = 255)
    private String nomeCliente;

    @Column(length = 50)
    private String contato;

    @Column(columnDefinition = "TEXT")
    private String endereco;

    @Column(nullable = false)
    private Boolean status = true;

    @Column(name = "usuario_criacao")
    private Long usuarioCriacao;
    
    @Column(name = "usuario_atualizacao")
    private Long usuarioAlteracao;

    @Column(nullable = true)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private LocalDateTime dataAtualizacao = LocalDateTime.now();
}
