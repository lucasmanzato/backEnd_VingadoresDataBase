package com.example.springboot.Controllers;

import com.example.springboot.dtos.AtribuicaoBaseRecordDto;
import com.example.springboot.models.AtribuicaoBase;
import com.example.springboot.models.Bases;
import com.example.springboot.models.Heroi;
import com.example.springboot.repositories.AtribuicaoBaseRepository;
import com.example.springboot.repositories.BasesRepository;
import com.example.springboot.repositories.HeroiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/atribuicoes")
public class AtribuicaoBaseController {

    @Autowired
    private AtribuicaoBaseRepository atribuicaoBaseRepository;

    @Autowired
    private HeroiRepository heroiRepository;

    @Autowired
    private BasesRepository basesRepository;

    // Endpoint para criar uma nova atribuição
    @PostMapping
    public ResponseEntity<AtribuicaoBase> createAtribuicao(@RequestBody AtribuicaoBaseRecordDto atribuicaoDto) {
        Heroi heroi = heroiRepository.findById(atribuicaoDto.heroiId())
                .orElseThrow(() -> new RuntimeException("Herói não encontrado com ID: " + atribuicaoDto.heroiId()));
        Bases base = basesRepository.findById(atribuicaoDto.baseId())
                .orElseThrow(() -> new RuntimeException("Base não encontrada com ID: " + atribuicaoDto.baseId()));

        AtribuicaoBase atribuicao = new AtribuicaoBase();
        atribuicao.setHeroi(heroi);
        atribuicao.setBase(base);

        AtribuicaoBase savedAtribuicao = atribuicaoBaseRepository.save(atribuicao);
        return new ResponseEntity<>(savedAtribuicao, HttpStatus.CREATED);
    }

    // Endpoint para listar todas as atribuições
    @GetMapping
    public ResponseEntity<List<AtribuicaoBase>> getAllAtribuicoes() {
        List<AtribuicaoBase> atribuicoes = atribuicaoBaseRepository.findAll();
        return new ResponseEntity<>(atribuicoes, HttpStatus.OK);
    }

    // Endpoint para buscar uma atribuição por ID
    @GetMapping("/{id}")
    public ResponseEntity<AtribuicaoBase> getAtribuicaoById(@PathVariable int id) {
        AtribuicaoBase atribuicao = atribuicaoBaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atribuição não encontrada com ID: " + id));
        return new ResponseEntity<>(atribuicao, HttpStatus.OK);
    }

    // Endpoint para atualizar uma atribuição
    @PutMapping("/{id}")
    public ResponseEntity<AtribuicaoBase> updateAtribuicao(@PathVariable int id, @RequestBody AtribuicaoBaseRecordDto atribuicaoDto) {
        AtribuicaoBase atribuicao = atribuicaoBaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atribuição não encontrada com ID: " + id));

        Heroi heroi = heroiRepository.findById(atribuicaoDto.heroiId())
                .orElseThrow(() -> new RuntimeException("Herói não encontrado com ID: " + atribuicaoDto.heroiId()));
        Bases base = basesRepository.findById(atribuicaoDto.baseId())
                .orElseThrow(() -> new RuntimeException("Base não encontrada com ID: " + atribuicaoDto.baseId()));

        atribuicao.setHeroi(heroi);
        atribuicao.setBase(base);

        AtribuicaoBase updatedAtribuicao = atribuicaoBaseRepository.save(atribuicao);
        return new ResponseEntity<>(updatedAtribuicao, HttpStatus.OK);
    }

    // Endpoint para deletar uma atribuição
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtribuicao(@PathVariable int id) {
        AtribuicaoBase atribuicao = atribuicaoBaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atribuição não encontrada com ID: " + id));

        atribuicaoBaseRepository.delete(atribuicao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
