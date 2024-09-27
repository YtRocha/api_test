package com.example.apitest.security.userdetails;

import com.example.apitest.model.usuario.Usuario;
import com.example.apitest.repository.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementação da UserDetailsService
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Instancia repositorio de usuario
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cria o UserDetailsImpl do usuario
     *
     * @param login
     * @return UserDetailsImpl do usuario
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuario não " +
                "encontrado"));

        return new UserDetailsImpl(usuario);
    }
}
