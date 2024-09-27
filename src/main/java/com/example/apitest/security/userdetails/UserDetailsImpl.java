package com.example.apitest.security.userdetails;


import com.example.apitest.model.usuario.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Implementação da UserDetails
 */
@Getter
public class UserDetailsImpl implements UserDetails {

    /**
     * Instancia Usuario
     */
    private final Usuario usuario;

    /**
     * Construtor da classe
     *
     * @param usuario
     */
    public UserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Converte os tipos de usuario em uma coleção de GrantedAuthorities
     * para representar os papeis de usuarios da forma que o spring trabalha
     * a collection possui apenas um elemento pois na aplicação o usuario possue apenas uma Role
     *
     * @return a coleção de um elemento do tipo GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getTipo().name()));
    }

    /**
     * Sobrecarga do metodo getPassword
     *
     * @return senha
     */
    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    /**
     * Sobrecarga do metodo getUsername
     *
     * @return login
     */
    @Override
    public String getUsername() {
        return usuario.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
