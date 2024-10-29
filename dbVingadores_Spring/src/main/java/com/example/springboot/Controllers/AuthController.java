package com.example.springboot.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/entrar")
    public String entrar() {
        return "entrar";
    }

    @PostMapping("/entrar")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        try {
            // Realiza a autenticação
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            return ResponseEntity.ok(authRequest.getUsername()+authRequest.getPassword()+" Login bem-sucedido!");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciais inválidas!");
        }
    }
}

// Classe para encapsular a requisição de login
class AuthRequest {
    private String username;
    private String password;

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
