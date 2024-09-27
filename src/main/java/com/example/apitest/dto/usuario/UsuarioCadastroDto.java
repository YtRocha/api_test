package com.example.apitest.dto.usuario;

import com.example.apitest.model.usuario.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioCadastroDto {

    private String login;
    private String nome;
    private String senha;
    private TipoUsuario tipo;

}
