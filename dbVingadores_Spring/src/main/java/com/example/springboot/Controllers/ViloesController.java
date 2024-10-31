package com.example.springboot.Controllers;

import com.example.springboot.dtos.ViloesRecordDto;
import com.example.springboot.models.Viloes;
import com.example.springboot.repositories.ViloesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/viloes")
public class ViloesController {

    @Autowired
    private ViloesRepository viloesRepository;

    // Endpoint para criar um novo vilão
    @PostMapping
    public ResponseEntity<Viloes> createVilao(@RequestBody ViloesRecordDto viloesDto) {
        Viloes vilao = new Viloes();
        vilao.setNomeViloes(viloesDto.nomeViloes());
        vilao.setTitulosViloes(viloesDto.titulosViloes());
        vilao.setPoderesViloes(viloesDto.poderesViloes());
        vilao.setMotivacaoViloes(viloesDto.motivacaoViloes());
        vilao.setStatusViloes(viloesDto.statusViloes());
        vilao.setLocalizacaoViloes(viloesDto.localizacaoViloes());

        Viloes savedVilao = viloesRepository.save(vilao);
        return new ResponseEntity<>(savedVilao, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os vilões
    @GetMapping
    public ResponseEntity<List<Viloes>> getAllViloes() {
        List<Viloes> viloes = viloesRepository.findAll();
        return new ResponseEntity<>(viloes, HttpStatus.OK);
    }

    // Endpoint para buscar um vilão por ID
    @GetMapping("/{id}")
    public ResponseEntity<Viloes> getVilaoById(@PathVariable int id) {
        Viloes vilao = viloesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vilão não encontrado com id: " + id));
        return new ResponseEntity<>(vilao, HttpStatus.OK);
    }

    // Endpoint para atualizar um vilão
    @PutMapping("/{id}")
    public ResponseEntity<Viloes> updateVilao(@PathVariable int id, @RequestBody ViloesRecordDto viloesDto) {
        Viloes vilao = viloesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vilão não encontrado com id: " + id));

        vilao.setNomeViloes(viloesDto.nomeViloes());
        vilao.setTitulosViloes(viloesDto.titulosViloes());
        vilao.setPoderesViloes(viloesDto.poderesViloes());
        vilao.setMotivacaoViloes(viloesDto.motivacaoViloes());
        vilao.setStatusViloes(viloesDto.statusViloes());
        vilao.setLocalizacaoViloes(viloesDto.localizacaoViloes());

        Viloes updatedVilao = viloesRepository.save(vilao);
        return new ResponseEntity<>(updatedVilao, HttpStatus.OK);
    }

    // Endpoint para deletar um vilão
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVilao(@PathVariable int id) {
        Viloes vilao = viloesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vilão não encontrado com id: " + id));

        viloesRepository.delete(vilao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
