package com.example.springboot.Controllers;

import com.example.springboot.dtos.MissoesRecordDto;
import com.example.springboot.models.Missoes;
import com.example.springboot.repositories.MissoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    @Autowired
    private MissoesRepository missoesRepository;

    // Endpoint para criar uma nova missão
    @PostMapping
    public ResponseEntity<Missoes> createMissao(@RequestBody MissoesRecordDto missoesDto) {
        Missoes missoes = new Missoes();
        missoes.setMissoesId(UUID.randomUUID());
        missoes.setNomeMissoes(missoesDto.nomeMissoes());
        missoes.setDescricaoMissoes(missoesDto.descricaoMissoes());
        missoes.setDataInicioMissoes(missoesDto.dataInicioMissoes());
        missoes.setDataTerminoMissoes(missoesDto.dataTerminoMissoes());
        missoes.setStatusMissoes(missoesDto.statusMissoes());
        missoes.setHeroisEnvolvidoMissoes(missoesDto.heroisEnvolvidoMissoes());
        missoes.setResultadoMissoes(missoesDto.resultadoMissoes());

        Missoes savedMissao = missoesRepository.save(missoes);
        return new ResponseEntity<>(savedMissao, HttpStatus.CREATED);
    }

    // Endpoint para listar todas as missões
    @GetMapping
    public ResponseEntity<List<Missoes>> getAllMissoes() {
        List<Missoes> missoes = missoesRepository.findAll();
        return new ResponseEntity<>(missoes, HttpStatus.OK);
    }

    // Endpoint para buscar uma missão por ID
    @GetMapping("/{id}")
    public ResponseEntity<Missoes> getMissaoById(@PathVariable UUID id) {
        Missoes missoes = missoesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada com id: " + id));
        return new ResponseEntity<>(missoes, HttpStatus.OK);
    }

    // Endpoint para atualizar uma missão
    @PutMapping("/{id}")
    public ResponseEntity<Missoes> updateMissao(@PathVariable UUID id, @RequestBody MissoesRecordDto missoesDto) {
        Missoes missoes = missoesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada com id: " + id));

        missoes.setNomeMissoes(missoesDto.nomeMissoes());
        missoes.setDescricaoMissoes(missoesDto.descricaoMissoes());
        missoes.setDataInicioMissoes(missoesDto.dataInicioMissoes());
        missoes.setDataTerminoMissoes(missoesDto.dataTerminoMissoes());
        missoes.setStatusMissoes(missoesDto.statusMissoes());
        missoes.setHeroisEnvolvidoMissoes(missoesDto.heroisEnvolvidoMissoes());
        missoes.setResultadoMissoes(missoesDto.resultadoMissoes());

        Missoes updatedMissao = missoesRepository.save(missoes);
        return new ResponseEntity<>(updatedMissao, HttpStatus.OK);
    }

    // Endpoint para deletar uma missão
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMissao(@PathVariable UUID id) {
        Missoes missoes = missoesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada com id: " + id));

        missoesRepository.delete(missoes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
