package com.example.apitest.security.authentication;


import com.example.apitest.model.usuario.Usuario;
import com.example.apitest.repository.usuario.UsuarioRepository;
import com.example.apitest.security.config.SecurityConfiguration;
import com.example.apitest.security.userdetails.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Instancia do service da jwt
     */
    @Autowired
    private JwtTokenService jwtTokenService;

    /**
     * Instancia de repositorio de usuario
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Verifica se a rota requer autenticação, caso necessite, verifica o jwt e depois continua a requesição
     * caso o jwt seja valido, caso não necessite de autenticação continua a requesição
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // verifica se requer autorização no endpoint
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request); // Recupera o token do cabeçalho da requisição
            if (token != null) {
                String subject = jwtTokenService.getSubjectFromToken(token); // obtem o subject (login)
                Usuario usuario = usuarioRepository.findByLogin(subject).get(); // busca usuario pelo login
                UserDetailsImpl userDetails = new UserDetailsImpl(usuario); // cria userDetails com o usuario

                // Cria um objeto de autenticação do Spring Security
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                // Define o objeto de autenticação no contexto de segurança do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new RuntimeException("O token está ausente.");
            }

        }
        filterChain.doFilter(request, response); // continua o processamento da requisição
    }

    /**
     * Recupera o token do cabeçalho Authorization da requisição
     *
     * @param request
     */
    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    // Verifica se o endpoint requer autenticação antes de processar a requisição

    /**
     * Verifica se o endpoint esta dentro dos endpoints publicos ou não
     *
     * @param request
     * @return true se não for publico
     */
    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        String requestURI = request.getRequestURI();
        return Arrays.stream(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED)
                .noneMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }
}
