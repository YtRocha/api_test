package com.example.apitest.service.usuario;

import com.example.apitest.dto.token.RecoveryJwtTokenDto;
import com.example.apitest.dto.usuario.UsuarioCadastroDto;
import com.example.apitest.dto.usuario.UsuarioLoginDto;
import com.example.apitest.mapper.usuario.UsuarioMapper;
import com.example.apitest.model.usuario.Usuario;
import com.example.apitest.repository.usuario.UsuarioRepository;
import com.example.apitest.security.authentication.JwtTokenService;
import com.example.apitest.security.config.SecurityConfiguration;
import com.example.apitest.security.userdetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    /**
     * Cria um novo usuario
     *
     */
    public void create(UsuarioCadastroDto usuarioCadastroDto) {
        try {
            Usuario usuario = usuarioMapper.cadastroDtoToEntity(usuarioCadastroDto);

            usuario.setSenha(securityConfiguration.passwordEncoder().encode(usuario.getSenha()));

            usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar usuario no" +
                    "banco de dados");
        }
    }

    /**
     * Metodo que autentica usuario
     *
     * @param usuarioLoginDto
     * @return jwt token
     */
    public RecoveryJwtTokenDto authenticateUser(UsuarioLoginDto usuarioLoginDto) {
        try {
            // Cria um objeto de autenticação
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(usuarioLoginDto.getLogin(), usuarioLoginDto.getSenha());


            // Autentica o usuário com as credenciais fornecidas
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);


            // Obtém o objeto UserDetails do usuário autenticado
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


            // Gera um token JWT para o usuário autenticado
            return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao autenticar o usuario");
        }
    }
}
