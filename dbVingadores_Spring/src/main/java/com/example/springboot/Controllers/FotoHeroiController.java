package com.example.springboot.Controllers;

import com.example.springboot.models.FotoHeroi;
import com.example.springboot.services.FotoHeroiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/fotos")
public class FotoHeroiController {

    @Autowired
    private FotoHeroiService fotoHeroiService;

    // Endpoint para listar todas as fotos de um herói específico
    @GetMapping("/heroi/{heroiId}")
    public List<FotoHeroi> listarFotosPorHeroi(@PathVariable Long heroiId) {
        return fotoHeroiService.listarFotosPorHeroi(heroiId);
    }

    // Endpoint para adicionar uma nova foto para um herói
    @PostMapping("/upload")
    public FotoHeroi uploadFoto(@RequestParam("heroiId") Long heroiId, @RequestParam("file") MultipartFile file) throws IOException {
        FotoHeroi fotoHeroi = new FotoHeroi();
        fotoHeroi.setHeroi(fotoHeroiService.getHeroiById(heroiId)); // Relaciona com o herói
        fotoHeroi.setImagem(file.getBytes()); // Salva a imagem como byte[] no banco

        return fotoHeroiService.salvarFoto(fotoHeroi);
    }

    // Endpoint para deletar uma foto específica
    @DeleteMapping("/{id}")
    public void deletarFoto(@PathVariable Long id) {
        fotoHeroiService.deletarFoto(id);
    }
    

}
