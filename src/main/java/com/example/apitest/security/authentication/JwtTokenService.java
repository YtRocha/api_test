package com.example.apitest.security.authentication;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.apitest.security.userdetails.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    private static final String SECRET_KEY = "W5THRIZZc3CvQehbZNVFqVdjSllsWRyx2h42RlAzHto=";

    private static final String ISSUER = "test-api";

    public String generateToken(UserDetailsImpl usuario) {
        try {
            // Definindo algoritmo HMAC SHA256 para criar a assinatura do token
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER) // Define o emissor do token
                    .withIssuedAt(creationDate()) // Define a data de emissão do token
                    .withExpiresAt(expirationDate()) // Define a data de expiração do token
                    .withSubject(usuario.getUsername()) // Define assunto do token (nesse caso o login)
                    .sign(algorithm); // Assina o token usando o algoritmo HMAC256
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }

    }

    public String getSubjectFromToken(String token) {
        try {
            // Define o algoritmo HMAC256 para verificar o token
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER) // Define o emissor do token
                    .build()
                    .verify(token) // Verifica a validade do token
                    .getSubject(); // Obtém o assunto (neste caso, o nome de usuário) do token
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token inválido ou expirado.");
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(2).toInstant();
    }

}


