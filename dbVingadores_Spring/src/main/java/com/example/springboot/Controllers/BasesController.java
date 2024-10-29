package com.example.springboot.Controllers;

import com.example.springboot.dtos.BasesRecordDto;
import com.example.springboot.models.Bases;
import com.example.springboot.repositories.BasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bases")
public class BasesController {

    @Autowired
    private BasesRepository basesRepository;

    // Endpoint para criar uma nova base
    @PostMapping
    public ResponseEntity<Bases> createBase(@RequestBody BasesRecordDto baseDto) {
        Bases base = new Bases();
        base.setBaseId(UUID.randomUUID());
        base.setNomeBase(baseDto.nomeBase());
        base.setLocalizacaoBase(baseDto.localizacaoBase());
        base.setPropositoBase(baseDto.propositoBase());
        base.setCapacidadeBase(baseDto.capacidadeBase());
        base.setStatusBase(baseDto.statusBase());
        base.setComandanteBase(baseDto.comandanteBase());

        Bases savedBase = basesRepository.save(base);
        return new ResponseEntity<>(savedBase, HttpStatus.CREATED);
    }

    // Endpoint para listar todas as bases
    @GetMapping
    public ResponseEntity<List<Bases>> getAllBases() {
        List<Bases> bases = basesRepository.findAll();
        return new ResponseEntity<>(bases, HttpStatus.OK);
    }

    // Endpoint para buscar uma base por ID
    @GetMapping("/{id}")
    public ResponseEntity<Bases> getBaseById(@PathVariable UUID id) {
        Bases base = basesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Base não encontrada com id: " + id));
        return new ResponseEntity<>(base, HttpStatus.OK);
    }

    // Endpoint para atualizar uma base
    @PutMapping("/{id}")
    public ResponseEntity<Bases> updateBase(@PathVariable UUID id, @RequestBody BasesRecordDto baseDto) {
        Bases base = basesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Base não encontrada com id: " + id));

        base.setNomeBase(baseDto.nomeBase());
        base.setLocalizacaoBase(baseDto.localizacaoBase());
        base.setPropositoBase(baseDto.propositoBase());
        base.setCapacidadeBase(baseDto.capacidadeBase());
        base.setStatusBase(baseDto.statusBase());
        base.setComandanteBase(baseDto.comandanteBase());

        Bases updatedBase = basesRepository.save(base);
        return new ResponseEntity<>(updatedBase, HttpStatus.OK);
    }

    // Endpoint para deletar uma base
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBase(@PathVariable UUID id) {
        Bases base = basesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Base não encontrada com id: " + id));

        basesRepository.delete(base);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
