package com.example.springboot.Controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8080/entrar")
public class UserController {

    @GetMapping("/user/home")
    public String userHome() {
        return "Bem-vindo, Usuário!";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "Bem-vindo, Admin!";
    }
}
