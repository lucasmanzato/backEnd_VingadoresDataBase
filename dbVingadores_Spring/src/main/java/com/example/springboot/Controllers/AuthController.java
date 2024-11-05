package com.example.springboot.controllers;

import com.example.springboot.models.Usuario;
import com.example.springboot.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entrar")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/admin")
    public ResponseEntity<String> adminLogin(@RequestBody AuthRequest authRequest) {
        Usuario usuario = usuarioRepository.findByUsername(authRequest.getUsername())
                .orElse(null);

        if (usuario != null && usuario.getPassword().equals(authRequest.getPassword()) && "ADMIN".equals(usuario.getRole())) {
            return ResponseEntity.ok("Login de administrador bem-sucedido!");
        } else {
            return ResponseEntity.status(403).body("Acesso negado! Somente administradores podem acessar esta página.");
        }
    }

    @PostMapping("/visitor")
    public ResponseEntity<String> visitorLogin(@RequestBody AuthRequest authRequest) {
        Usuario usuario = usuarioRepository.findByUsername(authRequest.getUsername())
                .orElse(null);

        if (usuario != null && usuario.getPassword().equals(authRequest.getPassword()) && "USER".equals(usuario.getRole())) {
            return ResponseEntity.ok("Login de visitante bem-sucedido!");
        } else {
            return ResponseEntity.status(403).body("Acesso negado! Somente visitantes podem acessar esta página.");
        }
    }
}

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
