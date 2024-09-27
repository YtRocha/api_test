package com.example.apitest.controller.usuario;

import com.example.apitest.dto.token.RecoveryJwtTokenDto;
import com.example.apitest.dto.usuario.UsuarioCadastroDto;
import com.example.apitest.dto.usuario.UsuarioLoginDto;
import com.example.apitest.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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
}
