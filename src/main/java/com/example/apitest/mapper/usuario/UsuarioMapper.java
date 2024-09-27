package com.example.apitest.mapper.usuario;

import com.example.apitest.dto.usuario.UsuarioCadastroDto;
import com.example.apitest.dto.usuario.UsuarioLoginDto;
import com.example.apitest.model.usuario.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario cadastroDtoToEntity(UsuarioCadastroDto usuarioCadastroDto);

    Usuario loginDtoToEntity(UsuarioLoginDto usuarioLoginDto);
}
