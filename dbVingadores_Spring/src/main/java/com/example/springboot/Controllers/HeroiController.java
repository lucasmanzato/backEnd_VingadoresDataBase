package com.example.springboot.Controllers;

import com.example.springboot.dtos.HeroiRecordDto;
import com.example.springboot.models.Heroi;
import com.example.springboot.repositories.HeroiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/herois")
public class HeroiController {

    @Autowired
    private HeroiRepository heroiRepository;

    // Endpoint para criar um novo herói
    @PostMapping
    public ResponseEntity<Heroi> createHeroi(@RequestBody HeroiRecordDto heroiDto) {
        Heroi heroi = new Heroi();
        heroi.setId(UUID.randomUUID());
        heroi.setNome(heroiDto.nome());
        heroi.setPoder(heroiDto.poder());
        heroi.setAfiliacao(heroiDto.afiliacao());
        heroi.setCodinome(heroiDto.codinome());
        heroi.setLocalizacao(heroiDto.localizacao());
        heroi.setStatus(heroiDto.status());

        Heroi savedHeroi = heroiRepository.save(heroi);
        return new ResponseEntity<>(savedHeroi, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os heróis
    @GetMapping
    public ResponseEntity<List<Heroi>> getAllHerois() {
        List<Heroi> herois = heroiRepository.findAll();
        return new ResponseEntity<>(herois, HttpStatus.OK);
    }

    // Endpoint para buscar um herói por ID
    @GetMapping("/{id}")
    public ResponseEntity<Heroi> getHeroiById(@PathVariable UUID id) {
        Heroi heroi = heroiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Herói não encontrado com id: " + id));
        return new ResponseEntity<>(heroi, HttpStatus.OK);
    }

    // Endpoint para atualizar um herói
    @PutMapping("/{id}")
    public ResponseEntity<Heroi> updateHeroi(@PathVariable UUID id, @RequestBody HeroiRecordDto heroiDto) {
        Heroi heroi = heroiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Herói não encontrado com id: " + id));

        heroi.setNome(heroiDto.nome());  
        heroi.setPoder(heroiDto.poder());


        Heroi updatedHeroi = heroiRepository.save(heroi);
        return new ResponseEntity<>(updatedHeroi, HttpStatus.OK);
    }

    // Endpoint para deletar um herói
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHeroi(@PathVariable UUID id) {
        Heroi heroi = heroiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Herói não encontrado com id: " + id));

        heroiRepository.delete(heroi);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
