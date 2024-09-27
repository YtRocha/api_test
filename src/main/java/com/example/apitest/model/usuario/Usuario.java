package com.example.apitest.model.usuario;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Model de usuario
 */
@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private TipoUsuario tipo;

}
