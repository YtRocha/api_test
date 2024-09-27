package com.example.apitest.controller.usuario;

import com.example.apitest.dto.token.RecoveryJwtTokenDto;
import com.example.apitest.dto.usuario.UsuarioCadastroDto;
import com.example.apitest.dto.usuario.UsuarioLoginDto;
import com.example.apitest.service.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuarios", description = "Operações para autenticação de usuarios, note que o crud de usuario não" +
        " esta completo, o objetivo era realizar apenas cadastro e login")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Rota que cadastra um usuario
     *
     * @param usuarioCadastroDto
     * @return httpstatus created
     */
    @Operation(summary = "Cadastrar usuario", description = "Cadastra usuario, note que pode se cadastrar como admin" +
            " pois é apenas para testes")
    @PostMapping
    ResponseEntity<?> cadastro(@RequestBody UsuarioCadastroDto usuarioCadastroDto) {
        try {
            usuarioService.create(usuarioCadastroDto);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", String.valueOf(e.getStatusCode()));
            errorResponse.put("error", e.getReason());
            errorResponse.put("message", "Ocorreu um erro ao cadastrar usuario.");
            errorResponse.put("details", e.getReason());

            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "500");
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", "Ocorreu um erro inesperado ao cadastrar usuario.");
            errorResponse.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Rota que permite que um usuario realize o login
     *
     * @param usuarioLoginDto
     * @return httpstatus ok
     */
    @Operation(summary = "Loga usuario")
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        try {
            RecoveryJwtTokenDto token = usuarioService.authenticateUser(usuarioLoginDto);
            return new ResponseEntity<>(token, HttpStatus.OK);

        }  catch (ResponseStatusException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", String.valueOf(e.getStatusCode()));
            errorResponse.put("error", e.getReason());
            errorResponse.put("message", "Ocorreu um erro ao autenticar usuario.");
            errorResponse.put("details", e.getReason());

            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "500");
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", "Ocorreu um erro inesperado ao autenticar usuario.");
            errorResponse.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    //// ***** AS ROTAS A SEGUIR SÃO APENAS PARA DEMONSTRAÇÃO ***** //////

    @Operation(summary = "Acesso com ou sem login", description = "Rota para testar sem login")
    @GetMapping("/deslogado")
    String rotaDeslogado() {
        return "Sou acessada mesmo sem login!";
    }

    @Operation(summary = "Acesso apenas com login", description = "Rota para testar com login")
    @GetMapping("/logado")
    String rotaLogado() {
        return "Estou logado!";
    }

    @Operation(summary = "Acesso apenas com admin", description = "Rota para testar como admin")
    @GetMapping("/admin")
    String admin() {
        return "Sou admin!";
    }
}
