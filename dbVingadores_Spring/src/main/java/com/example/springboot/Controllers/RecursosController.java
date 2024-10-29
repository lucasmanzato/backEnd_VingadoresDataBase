package com.example.springboot.Controllers;

import com.example.springboot.dtos.RecursosRecordDto;
import com.example.springboot.models.Recursos;
import com.example.springboot.repositories.RecursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recursos")
public class RecursosController {

    @Autowired
    private RecursosRepository recursoRepository;

    // Endpoint para criar um novo recurso
    @PostMapping
    public ResponseEntity<Recursos> createRecurso(@RequestBody RecursosRecordDto recursoDto) {
        Recursos recurso = new Recursos();
        recurso.setRecursoId(UUID.randomUUID());
        recurso.setTipoRecurso(recursoDto.tipoRecurso());
        recurso.setNomeRecurso(recursoDto.nomeRecurso());
        recurso.setDisponibilidadeRecurso(recursoDto.disponibilidadeRecurso());
        recurso.setUsuarioRecurso(recursoDto.usuarioRecurso());
        recurso.setLocalizacaoRecurso(recursoDto.localizacaoRecurso());

        Recursos savedRecurso = recursoRepository.save(recurso);
        return new ResponseEntity<>(savedRecurso, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os recursos
    @GetMapping
    public ResponseEntity<List<Recursos>> getAllRecursos() {
        List<Recursos> recursos = recursoRepository.findAll();
        return new ResponseEntity<>(recursos, HttpStatus.OK);
    }

    // Endpoint para buscar um recurso por ID
    @GetMapping("/{id}")
    public ResponseEntity<Recursos> getRecursoById(@PathVariable UUID id) {
        Recursos recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado com id: " + id));
        return new ResponseEntity<>(recurso, HttpStatus.OK);
    }

    // Endpoint para atualizar um recurso
    @PutMapping("/{id}")
    public ResponseEntity<Recursos> updateRecurso(@PathVariable UUID id, @RequestBody RecursosRecordDto recursoDto) {
        Recursos recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado com id: " + id));

        recurso.setTipoRecurso(recursoDto.tipoRecurso());
        recurso.setNomeRecurso(recursoDto.nomeRecurso());
        recurso.setDisponibilidadeRecurso(recursoDto.disponibilidadeRecurso());
        recurso.setUsuarioRecurso(recursoDto.usuarioRecurso());
        recurso.setLocalizacaoRecurso(recursoDto.localizacaoRecurso());

        Recursos updatedRecurso = recursoRepository.save(recurso);
        return new ResponseEntity<>(updatedRecurso, HttpStatus.OK);
    }

    // Endpoint para deletar um recurso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecurso(@PathVariable UUID id) {
        Recursos recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado com id: " + id));

        recursoRepository.delete(recurso);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
